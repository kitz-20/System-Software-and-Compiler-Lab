/* 
 SSC LAB ASSIGNMENT NO.3
DESIGN OF MACRO PASS 1
NAME : KETAKI PATIL
ROLL NO : PA-17
BATCH : A1
 */

package ssc_lab;

import java.util.*;
import java.io.*;
import java.io.BufferedReader;

public class lab3_macro {
	static List<String> MDT;
	static Map<String, String> MNT; 
	static Map<String, String> ALA;
	static List<String> outFile;
	static int mntPtr, mdtPtr, filePtr;
	static int argIndex = 1;
	
	public static void main(String[] args) {
		try {
			pass1();
		} 
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	static void pass1() throws Exception{
		
		//Initialize data structures
		MDT = new ArrayList<String>();
		MNT = new LinkedHashMap<String, String>();
		ALA = new LinkedHashMap<String, String>();
		outFile = new ArrayList<String>();
		mntPtr = 0;
		mdtPtr = 0;
		
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("lab3_input.txt")));
		String s;
		boolean procMacDef = false;
		boolean procMacName = false;
		
		System.out.println("SSC LAB 3 :"); 
        System.out.println("MACRO PASS 1\n"); 
		
		while((s=input.readLine())!=null) {
			String s_arr[] = tokenizeString(s, " ");
			
			String curToken = s_arr[0];
			
			if(curToken.equalsIgnoreCase("MACRO")) {
				procMacDef = true;
				procMacName = true;
				continue;
			}
			else {
				if(procMacDef == false && procMacName == false) {
					outFile.add(filePtr,s);
					filePtr++;
				}
			}
			
			if(procMacName == true) {
				
				MNT.put(curToken, mdtPtr+"");
				mntPtr++;
				procMacName = false;
				processArgumentList(s_arr[1]);
				MDT.add(mdtPtr,s);
				mdtPtr++;
				continue;
			}
			if(procMacDef == true) {
				String strIndexArg;
				strIndexArg = processArguments(s);
				MDT.add(mdtPtr, strIndexArg);
				mdtPtr++;
			}
			
			if(curToken.equalsIgnoreCase("MEND")) {
				procMacDef = false;
				continue;
			}
		}
			input.close();
			
			System.out.println("--------------MNT--------------");
			Iterator<String> itMNT = MNT.keySet().iterator();
			String key, mntRow, mdtRow;
			while(itMNT.hasNext()) {
				key = (String)itMNT.next();
				mntRow = key + " " + MNT.get(key);
				System.out.println(mntRow);
			}
			
			System.out.println("\n--------------ALA--------------");
			Iterator<String> itALA = ALA.keySet().iterator();
			String key1, alaRow;
			while(itALA.hasNext()) {
				key1 = (String)itALA.next();
				alaRow = key1 + " " + ALA.get(key1);
				System.out.println(alaRow);
			}
			
			System.out.println("\n--------------MDT--------------");
			for(int i = 0; i< MDT.size();i++) {
				mdtRow = i + " " + MDT.get(i);
				System.out.println(mdtRow);
			}
			System.out.println("\n-----------OUTPUT FILE------------");
			String outFileRow;
			for(int i = 0;i<outFile.size();i++) {
				outFileRow = i + " " + outFile.get(i);
				System.out.println(outFileRow);
			}
			
	}
	
	static String[] tokenizeString(String str, String seperator) {
		StringTokenizer st = new StringTokenizer(str, seperator, false);
		
		String s_arr[] = new String[st.countTokens()];
		for(int i=0; i<s_arr.length;i++) {
			s_arr[i] = st.nextToken();
		}
		return s_arr;
	}
	
	static void processArgumentList(String argList) {
		StringTokenizer st = new StringTokenizer(argList,",",false);
		int argCount = st.countTokens();
		String curArg;
		for(int i=1;i<=argCount;i++) {
			curArg = st.nextToken();
			ALA.put(curArg, "#"+argIndex);
			argIndex++;
		}
	}
	
	static String processArguments(String argList) {
		StringTokenizer st = new StringTokenizer(argList,",",false);
		int argCount = st.countTokens();
		String curArg, argIndexed;
		for(int i = 1; i<=argCount;i++) {
			curArg = st.nextToken();
			argIndexed = ALA.get(curArg);
			if(argIndexed == null) {
				continue;
			}
			argList = argList.replaceAll(curArg, argIndexed);
		}
		return argList;
	}
}
