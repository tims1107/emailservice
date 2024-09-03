package com.spectra.utils;


import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ParseUtil {
	
	private static LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

	private static Logger logger = lc.getLogger("Parser");

	public static boolean findStr(String data){
		boolean foundMatch = false;
		try {
			Pattern te = Pattern.compile("(329G|329M)");
			Matcher regexMatcher = te.matcher(data);
			foundMatch = regexMatcher.find();
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		return foundMatch;
	}

	public static boolean findPRE(String data){
		boolean foundMatch = false;
		try {
			Pattern te = Pattern.compile("(Positive|POSITIVE|Reactive|REACTIVE|Equivocal|EQUIVOCAL)", Pattern.MULTILINE);
			Matcher regexMatcher = te.matcher(data);
			foundMatch = regexMatcher.find();
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		return foundMatch;
	}

	public static boolean findDetected(String data){
		boolean foundMatch = false;
		try {
			Pattern te = Pattern.compile("(Not Detected|Detected)", Pattern.MULTILINE);
			Matcher regexMatcher = te.matcher(data);
			foundMatch = regexMatcher.find();
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		return foundMatch;
	}

	public static boolean findRE(String data){
		boolean foundMatch = false;
		try {
			Pattern te = Pattern.compile("(Reactive|REACTIVE|Equivocal|EQUIVOCAL)", Pattern.MULTILINE);
			Matcher regexMatcher = te.matcher(data);
			foundMatch = regexMatcher.find();
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		return foundMatch;
	}




	public static boolean findAnyString(String data, String pattern){
		boolean foundMatch = false;
		try {
			Pattern te = Pattern.compile(pattern, Pattern.MULTILINE);
			Matcher regexMatcher = te.matcher(data);
			foundMatch = regexMatcher.find();
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		return foundMatch;
	}
	public static String makeOBX(String subjectString, String[] replaceVal){
		StringBuffer resultString = new StringBuffer();
		try {

			Pattern regex = Pattern.compile("(\\{0\\})|(\\{1\\})|(\\{2\\})|([^\\|]+)|(|)");
			Matcher regexMatcher = regex.matcher(subjectString);
			String appendTail = "$4$5";
			while (regexMatcher.find()) {
				try {
					// You can vary the replacement text for each match on-the-fly
					if(regexMatcher.group(1) != null) {
						regexMatcher.appendReplacement(resultString, replaceVal[0]);
					} else if (regexMatcher.group(2) != null) {
						regexMatcher.appendReplacement(resultString, replaceVal[1]);
					} else if (regexMatcher.group(3) != null) {
						regexMatcher.appendReplacement(resultString, replaceVal[2]);
					}

					regexMatcher.appendReplacement(resultString, appendTail);

				} catch (IllegalStateException ex) {
					// appendReplacement() called without a prior successful call to find()
				} catch (IllegalArgumentException ex) {
					// Syntax error in the replacement text (unescaped $ signs?)
				} catch (IndexOutOfBoundsException ex) {
					// Non-existent backreference used the replacement text
				}
			}
			//regexMatcher.appendTail(resultString);
			return resultString.toString();
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}

		return null;

	}

	public static String makeOBX2(String subjectString, List<String> replaceVal){
		StringBuffer resultString = new StringBuffer();
		try {

			Pattern regex = Pattern.compile("([^\\|]*)(\\|?)");
			Matcher regexMatcher = regex.matcher(subjectString);

			List<MatchResult> matchList = new LinkedList<>();


			while (regexMatcher.find()) {

				matchList.add(regexMatcher.toMatchResult());
				try
				{
//					if(regexMatcher.group(1).equals("{0}")){
//						regexMatcher.appendReplacement(resultString, replaceVal[0] + "$2" );
//					} else if(regexMatcher.group(1).equals("{1}")){
//						regexMatcher.appendReplacement(resultString, replaceVal[1] + "$2" );
//					} else if(regexMatcher.group(1).equals("{2}")){
//						regexMatcher.appendReplacement(resultString, replaceVal[2] + "$2" );
//					} else {
//						regexMatcher.appendReplacement(resultString,"$1$2");
//					}




				} catch (IllegalStateException ex) {
					ex.printStackTrace();
					// appendReplacement() called without a prior successful call to find()
				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
					// Syntax error in the replacement text (unescaped $ signs?)
				} catch (IndexOutOfBoundsException ex) {
					ex.printStackTrace();
					// Non-existent backreference used the replacement text
				}
			}

			resultString.setLength(0);
			int repnum = 0;
			for(int i = 0; i < matchList.size();i++){
				MatchResult mr = matchList.get(i);

				//System.out.println(i + " " + mr.group(1));

				switch (i){
					case 1:
						resultString.append(replaceVal.get(0) + mr.group(2));
						break;
					case 3:
						resultString.append(replaceVal.get(1) + mr.group(2));
						break;
					case 5:
						resultString.append(replaceVal.get(2) + mr.group(2));
						break;
					default:
						resultString.append(mr.group());
				}



			}
			return resultString.toString();
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}

		return null;

	}

	
	public static enum TranType {
		OSU(1),POST(2),ADD(3),CANCEL(4);
		public  int value;
		
		TranType(int value){
			this.value = value;
		}
	}
	
	public static List<String> getAccount(String line){
		List<String> matchList = new ArrayList<String>();
		int count = 0;
		try {
			Pattern regex = Pattern.compile("(^4MED)([0-9]{5})(.*)?(FIRE$){1}");
			
			Matcher regexMatcher = regex.matcher(line);
			
			while (regexMatcher.find()) {
			
				matchList.add(regexMatcher.group(2));
				
				return matchList;
			
				
			} 
			
			
			
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		
		return null;
		
	}
	
	public static List<Integer> getResults(String line){
		List<Integer> matchList = new ArrayList<Integer>();
		int count = 0;
		try {
			Pattern regex = Pattern.compile("(\\x0B\\x01)?(\\d*)");
			
			Matcher regexMatcher = regex.matcher(line);
			
			while (regexMatcher.find()) {
				System.out.println(regexMatcher.groupCount());
				if(regexMatcher.group(2).length() > 0) {
					System.out.println(regexMatcher.group(2));
					matchList.add(Integer.parseInt(regexMatcher.group(2)));
				}
				
				
			
				
			} 
				
				
			
			
			
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		
		return matchList;
		
	}
	
	public static boolean isDone(String line){
		return line.matches("(?sm)(\\x0B)([^\\x1C]*)(\\x1C\\x0D)");
	}

	public static String retCode(String line){
		try {
			Pattern regex = Pattern.compile("^(.)(A1|A2|A5|B1|B2|B3|C1|D1|E1|F1|Z1|Z2)");
			Matcher regexMatcher = regex.matcher(line);
			if(regexMatcher.find())
			return regexMatcher.group(2);
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}

		return null;
	}

	public static String removeQuotes(String line){
		String resultString = null;
		try {
			 resultString = line.replaceAll("(\\\")", "");

		} catch (PatternSyntaxException ex) {
			ex.printStackTrace();
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		} catch (IndexOutOfBoundsException ex) {
			return "Not Found";
		}

		return resultString;

	}

	public static boolean isNPI(String npi){
		try {
			Pattern regex = Pattern.compile("\\d{10}");
			Matcher regexMatcher = regex.matcher(npi);
			if(regexMatcher.find())
				return true;
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}

		return false;
	}



	public static Map<String, String> getMap(String line, String pattern, List<String> grpnames) {
		Map<String, String> map = new HashMap<>();
		try {
			Pattern regex = Pattern.compile(pattern);
			Matcher regexMatcher = regex.matcher(line);
			if (regexMatcher.find()) {

				for (String s : grpnames){
					try {
						map.put(s, regexMatcher.group(s));
					} catch (Exception e){}


				}


			}
			return map;
		} catch (PatternSyntaxException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static List<String> getNPIField(String line){

		List<String> ResultString = new LinkedList<>();
		String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		Pattern te = null;
		String field = null;

		try {
			te = Pattern.compile("(\")(?<field>[^\"]+)(\"|\",)");

			for(String newline : fields) {
				Matcher regexMatcher = te.matcher(newline);
				if (regexMatcher.find()) {
					ResultString.add(regexMatcher.group("field"));
				} else {
					ResultString.add("");
				}

			}
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}


		return ResultString;
	}

	
	public static String ackmessage(String msh) {
		
		if(!msh.startsWith("MSH")) return null;
		
		List<String> message = splitLine(msh);
	
		try {
		
			msh = msh.replace(message.get(6),new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()));
			msh = msh.replace(message.get(8), "ACK^019");
			msh += "MSA|AA|" + message.get(9) + "|";

			
			return msh;

		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		} catch (IndexOutOfBoundsException e) {
			logger.error("Bad message: " + msh);
			
		}

		return null;

	}

	public static String replaceString(String adj_amt, String amt_due) {
		String result = null;

		if(Integer.valueOf(adj_amt) == (Integer.valueOf(amt_due) / 100)) {
			try {
				result = amt_due.replaceAll("(.{13})(.{2})", "$2$1");
			} catch (PatternSyntaxException ex) {
				// Syntax error in the regular expression
			} catch (IllegalArgumentException ex) {
				// Syntax error in the replacement text (unescaped $ signs?)
			} catch (IndexOutOfBoundsException ex) {
				// Non-existent backreference used the replacement text
			}

			return result;
		}

		return null;
	}

	
	public static String expandMSH(String line, String cid, String reqnum) {
		
		StringBuilder sb = new StringBuilder(0);
		StringBuilder grp = new StringBuilder(0);
		
		sb.append(line);
		String append;
		
		Map<Integer, MatchResult> mtch = new TreeMap<Integer, MatchResult>();
		Map<Integer, MatchResult> inFind = new TreeMap<Integer, MatchResult>();
		
		int count = 0;
		
		
		try {
			
			Pattern Regex = Pattern.compile("(.*?)(\\|)|(.*?\r)", Pattern.CANON_EQ);
			
			Pattern findGroup = Pattern.compile("(.*?)(\\^)", Pattern.CANON_EQ);
			
			
			
			Matcher m = Regex.matcher(line);

			//Find OBR matches and load map
			while (m.find()) {
				
				mtch.put(count++, m.toMatchResult() );
			}
			
			Matcher g = findGroup.matcher(mtch.get(4).group(1));
			
			count = 0;
			
			//Load map with testcode groups ^
			while(g.find()){
				inFind.put(count++, g.toMatchResult());
			}

			for(int n : inFind.keySet()){
				grp.append(inFind.get(n).group());
				
			}
			
			
			sb.replace(mtch.get(6).start(1), mtch.get(6).end(1), fmtdate());
			sb.insert(mtch.get(3).start(1), cid + "^");	
			
			

		} catch (PatternSyntaxException ex) {
			ex.printStackTrace();
		}
		
		
		return sb.toString();
	}
	
    public static String renumberOBR(String line, int num) {
		
		StringBuilder sb = new StringBuilder(0);
		StringBuilder grp = new StringBuilder(0);
		
		sb.append(line);
		String append;
		
		Map<Integer, MatchResult> mtch = new TreeMap<Integer, MatchResult>();
		Map<Integer, MatchResult> inFind = new TreeMap<Integer, MatchResult>();
		
		int count = 0;
		
		
		try {
			
			Pattern Regex = Pattern.compile("(.*?)(\\|)|(.*?\r)", Pattern.CANON_EQ);
			
			Pattern findGroup = Pattern.compile("(.*?)(\\^)", Pattern.CANON_EQ);
			
			
			
			Matcher m = Regex.matcher(line);

			//Find OBR matches and load map
			while (m.find()) {
				
				mtch.put(count++, m.toMatchResult() );
			}
			
			Matcher g = findGroup.matcher(mtch.get(4).group(1));
			
			System.out.println(mtch.get(1).group(1));
			
			
//			count = 0;
//			
//			//Load map with testcode groups ^
//			while(g.find()){
//				inFind.put(count++, g.toMatchResult());
//			}
//
//			for(int n : inFind.keySet()){
//				grp.append(inFind.get(n).group());
//				
//			}
//			
//			
			sb.replace(mtch.get(1).start(1), mtch.get(1).end(1), Integer.toString(num));
//			sb.insert(mtch.get(3).start(1), cid + "^");	
//			
//			

		} catch (PatternSyntaxException ex) {
			ex.printStackTrace();
		}
		
		
		return sb.toString();
	}


	private static String fmtdate(){
	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	
		return formatter.format(Calendar.getInstance().getTime());
	
	}
	
	
	
	public static String expandORC(String line, String reqnum) {
		
		StringBuilder sb = new StringBuilder(0);
		StringBuilder grp = new StringBuilder(0);
		
		sb.append(line);
		String append;
		
		Map<Integer, MatchResult> mtch = new TreeMap<Integer, MatchResult>();
		Map<Integer, MatchResult> inFind = new TreeMap<Integer, MatchResult>();
		
		int count = 0;
		
		
		try {
			
			Pattern Regex = Pattern.compile("(.*?)(\\|)|(.*?\r)", Pattern.CANON_EQ);
			
			Pattern findGroup = Pattern.compile("(.*?)(\\^)", Pattern.CANON_EQ);
			
			
			
			Matcher m = Regex.matcher(line);

			//Find OBR matches and load map
			while (m.find()) {
				
				mtch.put(count++, m.toMatchResult() );
			}
			
			Matcher g = findGroup.matcher(mtch.get(4).group(1));
			
			count = 0;
			
			//Load map with testcode groups ^
			while(g.find()){
				inFind.put(count++, g.toMatchResult());
			}

			for(int n : inFind.keySet()){
				grp.append(inFind.get(n).group());
				
			}
			
			
			sb.replace(mtch.get(2).start(1), mtch.get(2).end(1), reqnum);
			
			
			

		} catch (PatternSyntaxException ex) {
			ex.printStackTrace();
		}
		
		
		return sb.toString();
	}


	
	public static List<String> getFields(String line){
	
		List<String> matchList = new ArrayList<String>();
		int count = 0;
		try {
			Pattern regex = Pattern.compile("(?:\\^FD)([^\\^]*)(?:\\^FS)", Pattern.DOTALL | Pattern.MULTILINE);
			Matcher regexMatcher = regex.matcher(line);
			while (regexMatcher.find()) {
			
				matchList.add(regexMatcher.group(1));
				
				
			
				
			} 
			
			return matchList;
			
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		
		return null;
	}
	
	public static String getSource(String line){
		
		String source = null;
		int count = 0;
		try {
			Pattern regex = Pattern.compile("(?sm)(\\x0B)([^\\x1C].*)(\\x1C\\x0D|\\x1C)", Pattern.DOTALL | Pattern.MULTILINE);
			Matcher regexMatcher = regex.matcher(line);
			if (regexMatcher.find()) {
			
				source = regexMatcher.group(2);
							
				
			} 
			
			return source;
			
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		
		return null;
	}

	public static List<String> splitLine(String line){
		List<String> matchList = new ArrayList<String>();
		int count = 0;
		try {
			Pattern regex = Pattern.compile("([^\\|]*)?(\\|)?", Pattern.DOTALL | Pattern.MULTILINE);
			Matcher regexMatcher = regex.matcher(line);
			while (regexMatcher.find()) {
			
				matchList.add(regexMatcher.group(1));
				
				if(regexMatcher.group(2) == null) break;
			
				
			} 
			
			return matchList;
			
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		
		return null;
	
		
	}


	
	public static String replaceLine(String line){
		List<String> matchList = new ArrayList<String>();
		int count = 0;
		StringBuffer sb = new StringBuffer();
		
		try {
			Pattern regex = Pattern.compile("([^\\|]*)?(\\|)?", Pattern.DOTALL | Pattern.MULTILINE);
			Matcher regexMatcher = regex.matcher(line);
			while (regexMatcher.find()) {
			
				if(count != 1) {
					sb.append(regexMatcher.group(1));
					
				}
				
				count++;
				
				if(regexMatcher.group(2) != null) {
					 sb.append(regexMatcher.group(2));
				} else {
					break;
				}
				
				
			
				
			} 
			
			return sb.toString();
			
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		
		return null;
	
		
	}



	public static String replaceOBXLine(String line, Map<Integer, String> map){
		List<String> matchList = new ArrayList<String>();
		int count = 1;
		StringBuffer sb = new StringBuffer();

		try {
			Pattern regex = Pattern.compile("([^\\|]*)?(\\|)?", Pattern.DOTALL | Pattern.MULTILINE);
			Matcher regexMatcher = regex.matcher(line);
			while (regexMatcher.find()) {

				if(map.containsKey(count)){
					sb.append(map.get(count));
				} else {
					sb.append(regexMatcher.group(1));
				}


				if(regexMatcher.group(2) != null) {
					sb.append(regexMatcher.group(2));
				} else {
					break;
				}

				count++;




			}

			return sb.toString();

		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}

		return null;


	}
	
	public static List<String> hal_splitLine(String line){
		List<String> matchList = new ArrayList<String>();
		int count = 0;
		try {
			//Pattern regex = Pattern.compile("([^\"]*)?(\r)?", Pattern.DOTALL | Pattern.MULTILINE);
			Pattern regex = Pattern.compile("(\"[^\"]*\")");
			Matcher regexMatcher = regex.matcher(line);
			while (regexMatcher.find()) {
			
				matchList.add(regexMatcher.group(1));
				
			
				
			} 
			
			return matchList;
			
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		
		return null;
	
		
	}
	
	
	public static List<String> splitField(String line){
		List<String> matchList = new ArrayList<String>();
		int count = 0;
		try {
			Pattern regex = Pattern.compile("([^\\^]*)?(\\^)?", Pattern.DOTALL | Pattern.MULTILINE);
			Matcher regexMatcher = regex.matcher(line);
			while (regexMatcher.find()) {
			
				matchList.add(regexMatcher.group(1));
				if(regexMatcher.group(2) == null) break;
			} 
			
			return matchList;
			
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
		
		return null;
		
	}
	
public static Boolean getIsolate(String pattern, String line){
		
		try {
			Pattern regex = Pattern.compile(pattern, Pattern.DOTALL | Pattern.MULTILINE);
			Matcher regexMatcher = regex.matcher(line);
			if (regexMatcher.find()) {
			
				if( regexMatcher.group(2) != null){
					return true;
				}
				
			} 
			
		} catch (PatternSyntaxException ex) {
			ex.printStackTrace();
		}
		
		return false;
	
	}
	
	public static String getPart(String pattern, String line){
		
		try {
			Pattern regex = Pattern.compile(pattern, Pattern.DOTALL | Pattern.MULTILINE);
			Matcher regexMatcher = regex.matcher(line);
			if (regexMatcher.find()) {
			
				return regexMatcher.group(1);
				
			} 
			
		} catch (PatternSyntaxException ex) {
			ex.printStackTrace();
		}
		
		return null;
	
	}
	
	public static List<List<String>> parseLines(String source){
		List<List<String>> sourcelist = new ArrayList<List<String>>();
		String line;
		List<String> lines =  new ArrayList<String>();
		
		Scanner scan = new Scanner(source);
		while(scan.hasNextLine()){
			line = scan.nextLine() + "\r";
			
			try
			{
			if(Pattern.compile("(MSH|PID|PV1|ORC)").matcher(line).find()){
				lines.add(line);
				if(line.startsWith("ORC")){
					sourcelist.add(lines);
					lines = new ArrayList<String>();
				}
				
			} else if (Pattern.compile("(OBR)").matcher(line).find()){
				
					if(lines.size() > 0){
						
						sourcelist.add(lines);
						
						lines = new ArrayList<String>();
					}
										
					lines.add(line);
					
			
				
			}	else if (Pattern.compile("(DG1|OBX)").matcher(line).find()){
				lines.add(line);
			}
			
			} catch (Exception e){
				
				e.printStackTrace();
			}
				
			
		}
		scan.close();
		
		if(lines.size() > 0) {
			
			sourcelist.add(lines);
			return sourcelist;
		} 
		
		return null;
		
		
	}
	
	public static List<List<String>> parseLinesCRI(String source){
		List<List<String>> sourcelist = new ArrayList<List<String>>();
		String line;
		List<String> lines =  new ArrayList<String>();
		
		Scanner scan = new Scanner(source);
		while(scan.hasNextLine()){
			line = scan.nextLine() + "\r";
			
			try
			{
			if(Pattern.compile("(MSH|PID|PV1|)").matcher(line).find()){
				lines.add(line);
				if(line.startsWith("ORC")){
					sourcelist.add(lines);
					lines = new ArrayList<String>();
				}
				
			} else if (Pattern.compile("(OBR)").matcher(line).find()){
				
					if(lines.size() > 0){
						
						sourcelist.add(lines);
						
						lines = new ArrayList<String>();
					}
										
					lines.add(line);
					
			
				
			}	else if (Pattern.compile("(DG1|OBX)").matcher(line).find()){
				lines.add(line);
			}
			
			} catch (Exception e){
				
				e.printStackTrace();
			}
				
			
		}
		scan.close();
		
		if(lines.size() > 0) {
			
			sourcelist.add(lines);
			return sourcelist;
		} 
		
		return null;
		
		
	}



}
