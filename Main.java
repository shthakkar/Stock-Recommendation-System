import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) {
		try {
			String appendText = "";
		for(int i=0;i<5;i++){	
			String urlBuilder = "https://seekingalpha.com/earnings/earnings-news/"+(i+1);
			
		Document doc = Jsoup.connect( urlBuilder)
				.userAgent("Mozilla")
				.get();
		Elements elements = doc.body().getElementById("main_container").getElementById("earnings-center").getElementsByClass("insight-page-main").get(0).getElementsByClass("main-content").get(0).getElementById("analysis-list-container").getElementsByClass("media sa-c-item mc");
		for(Element element:elements){
		String text = element.getElementsByClass("media-body").get(0).getElementsByClass("media-heading").get(0).getElementsByTag("a").get(0).text();
		String date = element.getElementsByClass("media-body").get(0).getElementsByClass("article-desc").get(0).text();
		String symbolSentence = element.getElementsByClass("media-body").get(0).getElementsByClass("item-summary hidden").get(0).getElementsByClass("panel-body").get(0).getElementsByTag("ul").get(0).getElementsByTag("li").get(0).text();
		System.out.println(symbolSentence);
		String[] split1 = new String[0];
		String[] split2 = new String[0];
		String symbol;
		split1= symbolSentence.split(Pattern.quote("("));
		if(split1.length>1)
			 split2 = split1[1].split(Pattern.quote(")"));
		else
			symbol = symbolSentence;
		if(split2.length>=1)
			symbol= split2[0];
		else
			symbol = symbolSentence;
		int c = text.split("beats").length - 1;
		System.out.println(symbol);
		if(c==2
				&&
				(date.toUpperCase().contains("TODAY")||date.toUpperCase().contains("YESTERDAY"))
				){//beats both EPS and revenue
			appendText = appendText + "\n" + text + " SYMBOL ==> "+ symbol;
			
		}
		}
		}writeToFile(appendText);
		System.out.println(appendText	);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void writeToFile(String text) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			//String content = "This is the content to write into file\n";
String fileName = "/Google Drive/StockToBuy/stocksToBuy"+LocalDateTime.now()+".txt";
try {

    File file = new File(fileName);

    if (file.createNewFile()){
      System.out.println("File is created!");
    }else{
      System.out.println("File already exists.");
    }

	} catch (IOException e) {
		System.out.println("issues creating file");
    e.printStackTrace();
}
fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
			bw.write(text);

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		}		 finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
	}

}
