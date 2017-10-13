package tikape;

import spark.Spark;

public class Main {
	public static void main(String[] args) {
		Spark.get("/", (req, res) -> {
			return "Hello world!";
		});
	}
}
