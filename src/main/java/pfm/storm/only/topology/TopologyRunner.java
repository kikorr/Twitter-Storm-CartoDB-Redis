package pfm.storm.only.topology;

import pfm.storm.only.bolt.HashtagCounterRedis;
import pfm.storm.only.bolt.HashtagSplitter;
import pfm.storm.only.bolt.HttpURLConnectionCartoDB;
import pfm.storm.only.spout.TwitterStreamingSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class TopologyRunner {

	public static StormTopology createTopology(String consumerKey,
			String consumerSecret, String token, String secret) {

		TopologyBuilder builder = new TopologyBuilder();
        /*Insert terms to track on twitter*/
		builder.setSpout("tweet-stream", new TwitterStreamingSpout(consumerKey,
				consumerSecret, token, secret, new String[] { "Swiftair",
						"#zapeando166", "#StopGenocidioGaza","50 Sombras de Grey","Gaza","#StopGenocidioGaza","The Vamps","Tourmalet"  }), 1);

		builder.setBolt("hashtag-splitter", new HashtagSplitter(), 2)
				.shuffleGrouping("tweet-stream");

		builder.setBolt("hashtag-counter",
				new HashtagCounterRedis("localhost", 6379), 2).fieldsGrouping(
				"hashtag-splitter", new Fields("hashtag"));

		return builder.createTopology();
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		HttpURLConnectionCartoDB http = new HttpURLConnectionCartoDB();
		try {
			http.CleanDb();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StormTopology topology = createTopology("consumerKey twitter", //consumerKey twitter
				"consumerSecret twitter",		//consumerSecret twitter
				"AccessToken twitter",		//AccessToken twitter
				"AccessTokenSecret twitter");			//AccessTokenSecret twitter
		Config conf = new Config();
		conf.setDebug(true);

		conf.setMaxTaskParallelism(3);

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("pfm-twitter", conf, topology);

		Thread.sleep(90000000);

		cluster.shutdown();
	}

}
