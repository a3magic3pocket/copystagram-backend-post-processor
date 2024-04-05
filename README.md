# copystagram-backend-post-processor
- This is a Post Processor that converts Kafka string messages into mongoDB BsonObjectID.

# Environment
- jdk 11
- gradle 7.4.2

# How to use
- Export ObjectIdPostProcessor.java as a jar file.
- Move the ObjectIdPostProcessor.jar file to the mongoDB Sink Connector plugin directory.
