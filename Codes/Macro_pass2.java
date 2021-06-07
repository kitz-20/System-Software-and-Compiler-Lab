package ssc_lab;
import java.util.*;
import java.io.*;

public class Macro_pass2 {
	static List<String> MDT;
	static List<String> outFile;
	static Map<String, String> MNT;
	static int mntPtr, mdtPtr;
	static Map<String,String> APT;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			pass2();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	static void pass2() throws Exception{
		//Initialize data structure
		MDT = new ArrayList<String>();
		MNT = new LinkedHashMap<String, String>();
		APT = new HashMap<String,String>();
		outFile = new ArrayList<String>();
		mntPtr = 0; mdtPtr = 0;
		MNT.put("INCR", mdtPtr+"");
		MDT.add(mdtPtr,"INCR    &ARG1,&ARG2");
		mdtPtr++;
		MDT.add(mdtPtr,"ADD    AREG,#0");
		mdtPtr++;
		MDT.add(mdtPtr,"ADD    BREG,#1");
		mdtPtr++;
		MDT.add(mdtPtr,"MEND");
		mdtPtr++;
		
		MNT.put("DECR", mdtPtr+"");
		MDT.add(mdtPtr,"DECR    &AGR3,&ARG4");
		mdtPtr++;
		MDT.add(mdtPtr,"SUB    AREG,#2");
		mdtPtr++;
		MDT.add(mdtPtr,"SUB    BREG,#3");
		mdtPtr++;
		MDT.add(mdtPtr,"MEND");
		mdtPtr++;
		mntPtr++;
		
		System.out.println("SSC LAB 4 :"); 
        System.out.println("MACRO PASS 2\n"); 
        
		System.out.println("======== MNT ========");
		Iterator<String> itMNT = MNT.keySet().iterator();
		String key, mntRow, mdtRow,aptRow;
		while(itMNT.hasNext()) {
			key = (String)itMNT.next();
			mntRow = key + " " + MNT.get(key);
			System.out.println(mntRow);
		}
		
		
		System.out.println("========== MDT ==========");
		for(int i =0; i < MDT.size(); i++) {
			mdtRow = i + " " + MDT.get(i);
			System.out.println(mdtRow);
		}
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("input4.txt")));
		String s;
		boolean processingMacroCall = false;
		System.out.println("========== Pass 2 Output ==========");
		
		while((s = input.readLine())!=null) {
			Iterator<String> itMNT1= MNT.keySet().iterator();
			String mkey, mntRow1;
			String s_arr[]=tokenizeString(s," ");
			int flag=0;
			int f1=0;
			
			String curToken = s_arr[0];
			while(itMNT1.hasNext()) {
				key = (String)itMNT1.next();
				mntRow1 = key+ " " + MNT.get(key);
				String m_arr[] = tokenizeString(mntRow1,"    ");
				String curTokenMacro = m_arr[0];
				if(curTokenMacro.equalsIgnoreCase(curToken))
				{
					flag=1;
					APT = new HashMap<String, String>();
					String ap[] = tokenizeString(s_arr[1],",");
					for(int i = 0; i < ap.length; i++) {
							f1=1;
							APT.put(ap[i], "#"+i);
						}
					if(f1==1) {
						APT.put("D3","#2");
						APT.put("D4","#3");
					}
					
					int cmdtp=Integer.parseInt(m_arr[1]);
					int cmdtpn = cmdtp+1;
					mdtRow = MDT.get(cmdtpn);
					
					while(!(mdtRow.equalsIgnoreCase("MEND")))
					{
						
						String mdt_arr[] = tokenizeString(mdtRow," ");
						//System.out.print("this is mdt_arr[0] "+mdt_arr[0]+" and this is mdt_arr[1] "+mdt_arr[1]+"\n");
						System.out.print("+"+" "+mdt_arr[0]+" ");
						String mdt_par[]=tokenizeString(mdt_arr[1], ", ");
						//System.out.print("this is mdt_par[0] "+mdt_par[0]+ " and mdt_par[1] "+mdt_par[1]+"\n");
						
						for(int i=0;i<mdt_par.length;i++)
						{
							if(mdt_par[i].startsWith("#")) {
								//System.out.println("\nIam IN\n");
								Iterator<String> itALA = APT.keySet().iterator();
								String key1, alaRow;
								
								while(itALA.hasNext()) {
									key1=(String)itALA.next();
									String ind=APT.get(key1);
									//System.out.println("\nThis is ind : "+ind+"\n");
									//System.out.println("\nThis is mdt_par[i] : "+mdt_par[i]+"\n");
								
									if(mdt_par[i].equalsIgnoreCase(ind))
									{   
										
										System.out.println(key1);
									}
								}
							}
							
							else {
								System.out.print(mdt_par[i]+"  ");
							}
						}
						
						
						cmdtpn=cmdtpn+1;
						mdtRow=MDT.get(cmdtpn);
					}
				}
			}	
				
				if(flag==0) {
					//System.out.println(" ");
					System.out.println(s);
					//System.out.println("FLAG = 0");
					if(s=="END")
						break;
				}
			}
	
		
		
		}
	
	
static String[] tokenizeString(String str, String separator) {
	StringTokenizer st = new StringTokenizer(str,separator,false);
	String s_arr[]=new String[st.countTokens()];
	for(int i=0;i<s_arr.length;i++) {
		s_arr[i]=st.nextToken();
	}
	return s_arr;
}
}
