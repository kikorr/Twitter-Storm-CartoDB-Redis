package pfm.storm.only.bolt;

import org.apache.commons.lang.StringUtils;

import pfm.storm.only.basic.Tweet;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import java.util.Random;

public class HashtagSplitter extends BaseBasicBolt {

	private static final long serialVersionUID = 1L;

	public HashtagSplitter() {
	}

	private String normalizeString(String input) {
		String result = input;
		result = StringUtils.lowerCase(result);
		return result;
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		Tweet tweet = (Tweet) input.getValueByField("tweet");
		for (String hashtag : tweet.getHashtags()) {
			String hashtagNormalized = normalizeString(hashtag);
			String Longitude = String.valueOf(tweet.getLongitude());
			String Latitude = String.valueOf(tweet.getLatitude());
			Random randomGenerator = new Random();
			String Cartodb_Id = String.valueOf(randomGenerator
					.nextInt(1000000000));

			HttpURLConnectionCartoDB http = new HttpURLConnectionCartoDB();
			try {
				http.sendToDb(Cartodb_Id, Longitude, Latitude, hashtag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			collector.emit(new Values(hashtagNormalized, Longitude, Latitude,
					Cartodb_Id));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("hashtag", "longitude", "latitude",
				"Cartodb_Id"));
	}

}
