package com.bkatwal;

import java.util.*;


public class App
{

    static Set<String> processors = new HashSet<>();


    static class TLV
    {
        String type;
        int len;
        String value;

        @Override
        public String toString()
        {
            return "TLV{" +
                    "type='" + type + '\'' +
                    ", len=" + len +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    private static boolean isValidType(String type)
    {
        if (processors.contains(type))
            return true;
        else
            return false;
    }

    private static void initProcessorTypes()
    {
        processors.add("UPPRCS");
        processors.add("REPLCE");
    }

    public static void main(String[] args)
    {

        try
        {
            initProcessorTypes();
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNext())
            {
                String lineInFile = scanner.next();
                List<TLV> tlvs = parseLineIntoTLVs(lineInFile);
                for (TLV tlv : tlvs)
                {
                    if (!isValidType(tlv.type))
                    {
                        System.out.println("Type Not Valid");
                        continue;
                    }
                    processTLV(tlv);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * add all known processors in below conditons
     *
     * @param tlv
     */
    private static void processTLV(TLV tlv)
    {
        if ("UPPRCS".equalsIgnoreCase(tlv.type))
        {
            System.out.println("UPPRCS-" + tlv.value.toUpperCase());

        } else if ("REPLCE".equalsIgnoreCase(tlv.type))
        {
            System.out.println("REPLCE-THIS STRING");
        } else if ("LOWRCS".equalsIgnoreCase(tlv.type))
        {
            System.out.println("LOWRCS-" + tlv.value.toLowerCase());
        }
    }

    public void addNewProcessorType(String procType)
    {
        processors.add(procType);
    }


    private static List<TLV> parseLineIntoTLVs(String line)
    {
        List<TLV> tlvList = new ArrayList<>();
        long count = line.chars().filter(num -> num == '-').count();

        int hyphenCounter = 1;
        int startIndex = 0;
        for (int i = 1; i <= count / 2; i++)
        {
            int index1 = ordinalIndexOf(line, "-", hyphenCounter++);
            int index2 = ordinalIndexOf(line, "-", hyphenCounter++);

            String type = line.substring(startIndex, index1);
            int len = Integer.parseInt(line.substring(index1 + 1, index2));
            String val = line.substring(index2 + 1, index2 + len + 1);
            startIndex = index2 + len + 1;
            TLV tlv = new TLV();
            tlv.len = len;
            tlv.type = type;
            tlv.value = val;
            tlvList.add(tlv);
        }


        return tlvList;
    }

    public static int ordinalIndexOf(String str, String substr, int n)
    {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }

}
