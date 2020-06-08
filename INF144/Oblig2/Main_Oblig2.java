import java.io.*;
import java.util.*;

public class Main_Oblig2 {
	static PrintWriter txtfile;
	static int order = 1;
	static String eventyr;

	public static void main(String[] args) throws IOException {
		eventyr = ReadFile();
		int snitt = 0;
		int huffsnitt = 0;

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Notes\\INF144\\Oblig2\\Huffman Binary.bin"));

		File fileFolktale = new File("Notes\\INF144\\Oblig2\\Folktale.txt");
		File fileLZW = new File("Notes\\INF144\\Oblig2\\Huffman Binary.bin");

		LZW lzw = new LZW(eventyr);
		System.out.println("LZW of Folktale: \t" + eventyr.length() + " to " + lzw.output.length() + " chars or " + (lzw.output.length()*100)/eventyr.length() + " %.\n"); 
		System.out.println("Folktale:\t\t" + eventyr);
		System.out.println("LZW: \t\t\t" + lzw.output);

		txtfile = new PrintWriter("Notes\\INF144\\Oblig2\\LZW.txt");
		txtfile.print(lzw.output);
		txtfile.flush();

		Huffman huff = new Huffman(lzw.output);
		System.out.println("HuffmanLZW:\t\t" + Huffman.builder.toString() + "\n");

		System.out.println("Decoded HuffmanLZW:\t" + huff.getDecoded());
		System.out.println("DecompressedLZW:\t" + lzw.decompress(huff.getDecoded()) + "\n");
		
		txtfile = new PrintWriter("Notes\\INF144\\Oblig2\\Huffman.txt");
		txtfile.print(huff.output);
		txtfile.close();

		BitSet bs = GenerateBits(huff.output);
		oos.writeObject(bs);
		oos.close();

		for (order = 0; order <= 3; order++) {
			System.out.println("------ Computing average of 100 random texts of order: " + order + " ------");

			for (int i=0; i<100; i++) {
				String randomString = NewText();
				LZW newlzw = new LZW(randomString);
				Huffman newhuff = new Huffman(newlzw.output);

				txtfile = new PrintWriter("Notes\\INF144\\Oblig2\\LZW Average.txt");
				ObjectOutputStream newoos = new ObjectOutputStream(new FileOutputStream("Notes\\INF144\\Oblig2\\Huffman Average.bin"));
				BitSet newbs = GenerateBits(newhuff.output);

				txtfile.print(newlzw.output);
				newoos.writeObject(newbs);
				newoos.close();
				txtfile.flush();

				File newHuffFile = new File("Notes\\INF144\\Oblig2\\Huffman Average.bin");
				File newLzwFile = new File("Notes\\INF144\\Oblig2\\LZW Average.txt");

				huffsnitt += newHuffFile.length();
				snitt += newLzwFile.length();
			}
			//			System.out.println("Avg with " + order + " order from: " + eventyr.length() + " to " + snitt/100 + " chars or " + snitt/eventyr.length() + " %.");
			System.out.println("Only LZW with " + order + " order from: \t\t" 
					+ fileFolktale.length() + " to " + snitt/100 + " bytes or "+ (100-(snitt/fileFolktale.length())) + " %.");
			System.out.println("LZW and Huffman with " + order + " order from: \t" 
					+ fileFolktale.length() + " to " + huffsnitt/100 + " bytes or " + (100-(huffsnitt/fileFolktale.length())) + " %.\n");

			snitt = 0;
			huffsnitt = 0;
		}

		double compressed = fileFolktale.length() - fileLZW.length();
		System.out.println("Folktale\t\tHuffman + LZW\nOriginal: " + fileFolktale.length() + " bytes\tCompressed: " + fileLZW.length() + " bytes\tSaved: " + compressed + " bytes");
		
	}

	private static BitSet GenerateBits(String string) {
		BitSet bitset = new BitSet(string.length());
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == '1') {
				bitset.set(i);
			}
		}
		return bitset;
	}



	public static String NewText() {
		HashMap<String, ArrayList<Character>> map = new HashMap<>();

		int characters = eventyr.length();

		for(int i=0; i< eventyr.length() - order; i++) {
			String string = eventyr.substring(i, i + order);

			if (!map.containsKey(string)) {
				map.put(string, new ArrayList<>());
			}
			map.get(string).add(eventyr.charAt(i+order));
		}
		double random = (eventyr.length() - order) * Math.random();
		String currentString = eventyr.substring((int) random, (int) random + order);
		String newString = currentString;     

		for (int j = 0; j <characters; j++) {
			ArrayList<Character> choices = map.get(currentString);
			double outRandom = Math.random() * (choices.size());
			newString += choices.get((int) outRandom);
			int length = newString.length();
			currentString = newString.substring(length - order, length); 

		}
		return newString;
	}

	private static String ReadFile() { 
		InputStreamReader inputSR = new InputStreamReader(System.in); 
		BufferedReader bufferedR = new BufferedReader(inputSR); 
		String text = "";
		try {
			String line = null;
			File file = new File("Notes\\INF144\\Oblig2\\Folktale.txt");

			bufferedR = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

			while((line = bufferedR.readLine()) != null){
				text += line.replaceAll("\\<.*?>", "").replaceAll(" +", " ").toLowerCase();
			}
		} catch(IOException ioe) { 
			System.out.println("Error reading from file");
		} 
		return text; 
	}
}
