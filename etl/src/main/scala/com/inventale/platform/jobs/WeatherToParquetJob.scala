package com.inventale.platform.jobs

import java.util.TimeZone

import com.amazonaws.services.glue.GlueContext
import com.amazonaws.services.glue.log.GlueLogger
import com.amazonaws.services.glue.util.{GlueArgParser, Job, JsonOptions}
import com.typesafe.config.ConfigFactory
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.JavaConversions.mapAsJavaMap

object WeatherToParquetJob {
  def main(args: Array[String]): Unit = {
    val logger = new GlueLogger
    TimeZone.setDefault(TimeZone.getTimeZone("UTC")) // ensure UTC timezone for time-related functions
    val conf = ConfigFactory.load().getConfig("etl")
    logger.info(s"Config: $conf")

    val glueArgs: Map[String, String] = GlueArgParser.getResolvedOptions(args, Seq("JOB_NAME").toArray)
    val sparkConf = new SparkConf()
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.app.name", this.getClass.getSimpleName)
    val spark: SparkContext = SparkContext.getOrCreate(sparkConf)
    val glueContext: GlueContext = new GlueContext(spark)

    // init context to enable Glue features
    Job.init(glueArgs("JOB_NAME"), glueContext, mapAsJavaMap(glueArgs))

    val inputDataFrame = glueContext.getCatalogSource(
      database = conf.getString("weatherToParquet.database"),
      tableName = conf.getString("weatherToParquet.inputTable"),
      transformationContext = "WeatherToParquetJob_source"
    ).getDynamicFrame()

    logger.info(s"Input frame schema: ${inputDataFrame.schema}")

    // handle case when there are no new data
    if (inputDataFrame.count > 0) {
      // choose right types for fields if there are few options
      val dynamicFrame =
        inputDataFrame
        .resolveChoice(
          specs = Seq(
            ("humidity9am","cast:long"),
            ("humidity3pm","cast:long"),
            ("pressure9am","cast:double"),
            ("pressure3pm","cast:double"),
            ("temp9am","cast:double"),
            ("temp3pm","cast:double")
          )
        )

      logger.info(s"Dynamic frame schema: ${dynamicFrame.schema}")
      // write to parquet using partitions by "location"
      glueContext.getSinkWithFormat(
        "s3",
        JsonOptions(
          Map(
            "path" -> conf.getString("weatherToParquet.outputPath"),
            "partitionKeys" -> Seq("location")
          )
        ),
        "WeatherToParquetJob_sink",
        "parquet"
      ).writeDynamicFrame(dynamicFrame)
    }

    // commit job to save bookmarks
    Job.commit()
  }
}
