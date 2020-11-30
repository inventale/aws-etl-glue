### Overview

**Please keep in mind:** in links below use real aws account (e.g. inventale dev sandbox) instead of faked {9999999}

* This project contains Scala `WeatherToParquetJob` job to convert the data from sample to parquet using [AWS Glue](https://aws.amazon.com/glue/) and store them to s3 bucket.
* Code located here: https://s3.console.aws.amazon.com/s3/buckets/etl-glue-eu-west-1-{9999999}-code?region=eu-west-1&tab=objects
* Input data: https://s3.console.aws.amazon.com/s3/buckets/etl-glue-eu-west-1-{9999999}-input?region=eu-west-1&prefix=input/&showversions=false
* Result data: https://s3.console.aws.amazon.com/s3/buckets/etl-glue-eu-west-1-{9999999}-db?region=eu-west-1&prefix=weather/&showversions=false
* You can query both input and output data using [Athena](https://eu-west-1.console.aws.amazon.com/athena/home?region=eu-west-1#query)
* Please find full used dataset here: https://www.kaggle.com/jsphyg/weather-dataset-rattle-package
* You can find cloudformation infrastructure descriptors here: `./aws/`

### Used Glue features

* [Crawlers](https://docs.aws.amazon.com/glue/latest/dg/add-crawler.html) to populate input and output data into Glue Catalogue
  You can see them in AWS Console: https://eu-west-1.console.aws.amazon.com/glue/home?region=eu-west-1#catalog:tab=crawlers fo
* [Jobs](https://docs.aws.amazon.com/glue/latest/dg/author-job.html) as a Spark-based computation engine
  You can see them in AWS Console: https://eu-west-1.console.aws.amazon.com/glue/home?region=eu-west-1#etl:tab=jobs
* [Dynamic Frame](https://docs.aws.amazon.com/glue/latest/dg/aws-glue-api-crawler-pyspark-extensions-dynamic-frame.html) similar to a Spark DataFrame abstraction,
  except that each record is self-describing, so no schema is required initially. Instead, AWS Glue computes a schema on-the-fly when required,
  and explicitly encodes schema inconsistencies using a choice (or union) type
* [Bookmarks](https://docs.aws.amazon.com/glue/latest/dg/monitor-continuations.html) to track data that
  has already been processed during a previous run of an ETL job by persisting state information from the job run
* [Metrics](https://docs.aws.amazon.com/glue/latest/dg/monitor-cloudwatch.html) are automatically exported
  You can see them in AWS Console: https://eu-west-1.console.aws.amazon.com/cloudwatch/home?region=eu-west-1#metricsV2:graph=~();query=~'*7bGlue*2cJobName*2cJobRunId*2cType*7d
* Triggers to run jobs on schedule
  You can see them in AWS Console: https://eu-west-1.console.aws.amazon.com/glue/home?region=eu-west-1#etl:tab=triggers

### Infrastructure use cases
* Build project: `./gradlew clean build`

* You can see all outdated dependencies using the command `./gradlew dependencyUpdates`

* You can see information about build and tests `./gradlew clean test --scan`

* `etl-local` module contains necessary dependencies to run jobs with local Glue libraries.
  However, some features like bookmarks or querying data from the Glue Catalogue are not supported.
