package com.example.appcenter.companion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by appcenter on 5/22/17.
 */

public class CSVParser {
    InputStream inputStream;

    public CSVParser(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }

    public List read(){
        List resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try
        {
            String csvLine;
            String finalRow[]=new String[3];
            while((csvLine=reader.readLine())!=null)
            {
                String[] row =  csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if(!row[0].isEmpty())
                {
                    resultList.add(finalRow);
                    finalRow= new String[3];
                    finalRow[0] = (row[0]);
                    finalRow[1] = (row[1]);
                    finalRow[2] = (row[2]);

                }else
                {
                    finalRow[2]+="\n"+(row[2]);
                }

            }
            //First two are just headers so removing them
            resultList.remove(0);
            resultList.remove(0);
        }catch (IOException ex)
        {
            throw new RuntimeException("Error reading CSV file:"+ex);
        }
        finally {
            try {
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error while closing input stream:"+e);
            }
        }
        return resultList;
    }

}
