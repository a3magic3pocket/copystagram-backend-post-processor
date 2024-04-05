package sinkPostProcessor;

import org.bson.types.ObjectId;

import org.apache.kafka.connect.sink.SinkRecord;
import org.bson.BsonObjectId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.kafka.connect.sink.MongoSinkTopicConfig;
import com.mongodb.kafka.connect.sink.converter.SinkDocument;
import com.mongodb.kafka.connect.sink.processor.PostProcessor;

import static com.mongodb.kafka.connect.sink.MongoSinkTopicConfig.VALUE_PROJECTION_LIST_CONFIG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectIdPostProcessor extends PostProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(PostProcessor.class);
	private final List<String> fieldNames;

	public ObjectIdPostProcessor(final MongoSinkTopicConfig config) {
		super(config);
		String rawFields = config.getString(VALUE_PROJECTION_LIST_CONFIG);
		fieldNames = new ArrayList<String>(Arrays.asList(rawFields.split(",")));
	}

	@Override
	public void process(final SinkDocument doc, final SinkRecord orig) {
		doc.getValueDoc().ifPresent(vd -> {
			for (String fieldName : fieldNames) {
				if (vd.containsKey(fieldName)) {
					String hash = vd.getString(fieldName).getValue();
//					LOGGER.warn("+++" + hash);
					
					vd.put(fieldName, new BsonObjectId(new ObjectId(hash)));
				}
			}
		});
	}

}
