package com.example.appcenter.companion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by appcenter on 6/5/17.
 */

public class XMLParser {
    private XmlPullParser xmlParser;
    private InputStream xmlFileInputStream;
    private SharedPreferences sharedPref;
    String qID,qPicture,correctAnswer,qPictureCourtesy;
    String possible[]= new String[4];

    public String[] getChoices()
    {
        return possible;
    }
    public String qetQuestionNumber()
    {
        return qID;
    }
    public String getPictureName()
    {
        return qPicture.replace(".jpg","");
    }
    public int getCorrectAnswer()
    {
        return Integer.parseInt(correctAnswer);
    }
    public String getPictureReferences()
    {
        return qPictureCourtesy;
    }

    public void closeFileStream()
    {
        try {
            //close the file
            xmlFileInputStream.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void initializeXMLParser()throws XmlPullParserException,IOException
    {
            xmlParser = Xml.newPullParser();
            xmlParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlParser.setInput(xmlFileInputStream, null);
            xmlParser.nextTag();
    }
    private boolean checkIfQuestionIsAnswered(String qID)
    {
        return sharedPref.getBoolean(qID,false);
    }
    private void skipQuestion()throws XmlPullParserException,IOException
    {

        while (xmlParser.next() != XmlPullParser.END_DOCUMENT) {
            if (xmlParser.getEventType() != XmlPullParser.START_TAG)
                continue;
            if(xmlParser.getName().equals("question"))
                break;
        }
    }
    private String readText()throws IOException, XmlPullParserException
    {
        String result = "";
        if(xmlParser.next()==XmlPullParser.TEXT)
        {
            result = xmlParser.getText();
            xmlParser.nextTag();
        }
        return result;
    }
    public void parseNextQuestionInformation()throws XmlPullParserException,IOException
    {
        int index=0;
        while (xmlParser.next() != XmlPullParser.END_DOCUMENT) {
            if(xmlParser.getEventType()!=XmlPullParser.START_TAG)
                continue;
            String name = xmlParser.getName();

            switch (name) {
                case "qID":              qID=readText();
                    if(checkIfQuestionIsAnswered(qID))
                    {
                        skipQuestion();
                    }
                    break;
                case "qPicture":         qPicture=readText();
                    break;
                case "possible":         possible[index%4]=readText();
                    index++;
                    break;
                case "qPictureCourtesy": qPictureCourtesy=readText();
                    break;
            }
            //If last one is read then break the loop
            if(name.equals("correctAnswers"))
            {
                correctAnswer = readText();
                break;
            }
        }
    }
    public void markQuestionAsRead()
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(qID,true);
        editor.commit();
    }
    public XMLParser(Context context)
    {
        try{
        sharedPref = context.getSharedPreferences(context.getResources().getString(R.string.identify_preference_file_key),context.MODE_PRIVATE);
        //get XML file
        xmlFileInputStream= context.getResources().openRawResource(R.raw.containerid);
        initializeXMLParser();
    }catch (Exception e)
    {
        e.printStackTrace();
    }
    }

}
