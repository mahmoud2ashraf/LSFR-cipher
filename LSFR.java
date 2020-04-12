/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsfr;

import java.util.*;
import java.io.*;

/**
 *
 * @author Mahmoud
 */
public class LSFR {

    /**
     * @param args the command line arguments
     */
    Vector<Integer> intial = new Vector();
    Vector<Integer> p = new Vector();
    Vector <Integer> register = new Vector();
    Vector <Integer> used = new Vector();
    int x;
    String stream = "";

    void read_file(String path) throws FileNotFoundException, IOException {
        intial.clear();
        p.clear();
        register.clear();
        used.clear();
        
        File f1 = new File (path);
        BufferedReader b1 = new BufferedReader(new FileReader(f1));
        String read = "";
        read = b1.readLine();
        //read the intial values and put it  in a vector
        for (int i = 0; i < read.length(); i++) {

            int a = Integer.parseInt(read.charAt(i) + "");
            intial.add(a);

        }
        read = "";
        read = b1.readLine();
        //read the p(x) and put itin a vector
        for (int i = 0; i < read.length(); i++) {

            int a = Integer.parseInt(read.charAt(i) + "");
            p.add(a);

        }
        read = "";
        //read the warm up numbers 
        read = b1.readLine();
        x = Integer.parseInt(read);
    }
    void write_to_file(String path2 , String o) throws IOException
    {
       File f2 = new File(path2);
       FileWriter w1 = new FileWriter(f2);
        w1.write(o);
        
        w1.close();
    }
    void change_to_binary(String text)
    {
        
        for(int i=0 ; i<text.length() ; i++)
        {
            int a = (int) text.charAt(i);
            String binary = Integer.toBinaryString(a);
            if(text.charAt(i) == (' '))
            {
               stream =stream + "0";
            }
            
            stream = stream + binary;
            
        }
        //System.out.println(stream + "hello");
    }
    void enc() throws IOException
    {
        String key = "";
        String output = "";
        register = (Vector) intial.clone();
        for(int i=0 ; i<p.size() ; i++)
        {
            if(p.get(i)==1)
            {
                used.add(i);
            }
        }
        int s = x + stream.length();
        //System.out.println(s);
        //System.out.println(stream.length());
        boolean flag = true;
        for(int i =0 ; i<s ; i++)
        {
            
            int u = register.get(used.get(0));
            for(int j=1 ; j<used.size() ; j++)
            {
                 u = register.get(used.get(j)) ^ u ;
            }
           String out = Integer.toString(register.get(0));
            key = key + out;
            register.remove(0);
            register.add(u);
            if(key.length()==x && flag == true)
            {
                System.out.println("warm up bits : " + key);
                key = "";
                flag = false;
            }
        }
        for(int i =0 ; i<stream.length() ; i++)
        {
           int fi  = Integer.parseInt(stream.charAt(i)+ "")^Integer.parseInt(key.charAt(i) + "");
           output = output  + Integer.toString(fi);
        }
        //System.out.println(output);
        write_to_file("encryption.txt", output);
    }
    
    void dec(String path2) throws FileNotFoundException, IOException
    {
          String k , o , final_text = ""  , buffer = "";
        File f1 = new File (path2);
        BufferedReader b1 = new BufferedReader(new FileReader(f1));
        String read = "";
          read = b1.readLine();
        o = read;
        
        
        String key = "";
        String output = "";
        register = (Vector) intial.clone();
        for(int i=0 ; i<p.size() ; i++)
        {
            if(p.get(i)==1)
            {
                used.add(i);
            }
        }
        int s = x + o.length();
        //System.out.println(s);
        //System.out.println(stream.length());
        boolean flag = true;
        for(int i =0 ; i<s ; i++)
        {
            
            int u = register.get(used.get(0));
            for(int j=1 ; j<used.size() ; j++)
            {
                 u = register.get(used.get(j)) ^ u ;
            }
           String out = Integer.toString(register.get(0));
            key = key + out;
            register.remove(0);
            register.add(u);
            if(key.length()==x && flag == true)
            {
                System.out.println("warm up bits : " + key);
                key = "";
                flag = false;
            }
        }
        
        
    
       // read = b1.readLine();
        //k = read;
       // read = "";
      
       // System.out.println(k);
       // System.out.println(o);
        for(int i=0 ; i<o.length() ; i++)
        {
            //f is final 
            int f = Integer.parseInt(o.charAt(i)+"") ^ Integer.parseInt(key.charAt(i) + "");
            buffer = buffer + Integer.toString(f);
        }
        //System.out.println(buffer + "hhh");
        String c = "";
        for(int i=0 ; i<buffer.length() ; i++)
        {
            c+=buffer.charAt(i) + "";
            if(c.length()==7)
            {
                //System.out.println(c);
                int decimal= Integer.parseInt(c,2);
                final_text+= (char) decimal ;
                c = "";
            }
            
        }
        System.out.println(final_text);
    }

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        LSFR l1 = new LSFR();
      //  Scanner s1 = new Scanner(System.in);
     // l1.read_file("E:\\mahmoud\\university\\third year - second term\\security\\LSFR\\input.txt");
     //  System.out.println("enter the text : ");
    //  String in = s1.nextLine();
     //  l1.change_to_binary(in);
       // l1.enc();
        l1.read_file("E:\\mahmoud\\university\\third year - second term\\security\\LSFR\\input.txt");
          l1.dec("encryption.txt");
    }

}
