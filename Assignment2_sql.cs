using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace assignment2
{
    class Assignment2
    {
        
        public void Index(string DBFilePath, string vectorFilePath)
        {
            if (DBFilePath == null || vectorFilePath == null)
                return;
            try
            {
                string[][] Lines;
                Lines = File.ReadLines(DBFilePath).Where(line => line != "" && line != null && line != ",,,,,,,,,").Select(x => x.Split(',')).ToArray();
                int Columns = Lines[0].Count();
                using (StreamWriter sw = new StreamWriter(vectorFilePath, false))
                {
                    sw.Write("");
                    sw.Close();
                }
                for (int i = 1; i < Lines[0].Count(); i++)
                {
                    string[] ColumnValues = FindValues(Lines, i);
                    CreateVector(ColumnValues, Lines, vectorFilePath, i);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("The file could not be found :(");
                Console.ReadLine();
                Console.WriteLine(e.Message);
            }
        }

        private string[] FindValues(String[][] Lines,int Column)
        {
            List<string> Values = new List<string>();
            for (int i = 0; i < Lines.Count(); i++)
            {
                string currValue = Lines[i][Column];
                if (!Values.Contains(currValue))
                    Values.Add(currValue);
            }
            return Values.ToArray();
        }

        private void CreateVector(string[] ColumnValues, String[][] Lines, string path, int Column)
        {
            using (StreamWriter sw = new StreamWriter(path, true))
            {
                
                for (int i = 1; i < ColumnValues.Count(); i++)
                {
                    string Line = Lines[0][Column];
                    string CurrValue = ColumnValues[i];
                    Line = Line + "," + ColumnValues[i] + ",";
                    for (int j = 1; j < Lines.Count(); j++)
                    {
                        if(Lines[j][Column] == CurrValue)
                            Line = Line + "1";

                        else
                            Line = Line + "0";
                    }
                    sw.WriteLine(Line);
                }
                sw.Close();
            }
        }
     
       
        public List<string> SelectVectors(XmlDocument xmlDoc, string vectorFilePath)
        {
            if (xmlDoc == null || vectorFilePath == null)
                return null;
            XmlNodeList Columns = xmlDoc.SelectNodes("DB_EX2_QUERY/Query_Elements/Element");
            String[] SearchFor = new string[2];
            List<string> results = new List<string>();
            if (Columns.Count == 0 || Columns == null)
            {
                using (StreamReader sr = new StreamReader(vectorFilePath))
                {
                    while (!sr.EndOfStream)
                    {
                        string Line = sr.ReadLine();
                        String[] LineValues;
                        LineValues = Line.Split(new char[] { ',' });
                        if (LineValues.Count() == 1)
                        {
                            String temp = LineValues[0];
                            LineValues = new string[3];
                            LineValues[0] = temp;
                            LineValues[1] = sr.ReadLine();
                            LineValues[2] = sr.ReadLine();
                            LineValues[1] = LineValues[1].Substring(1);
                            LineValues[2] = LineValues[2].Substring(1);
                        }
                        results.Add(LineValues[2]);
                    }
                    sr.Close();
                }
            }
            else
            {
                for (int i = 0; i < Columns.Count; i++)
                {
                    XmlNode Column = Columns[i];
                    SearchFor[0] = Column.Attributes[0].InnerText;
                    XmlNodeList ColumnValues = Column.ChildNodes;
                    for (int j = 0; j < ColumnValues.Count; j++)
                    {
                        XmlNode ColumnValue = ColumnValues[j];
                        SearchFor[1] = ColumnValue.InnerText;
                        // Console.WriteLine(SearchFor);
                        using (StreamReader sr = new StreamReader(vectorFilePath))
                        {
                            while (!sr.EndOfStream)
                            {
                                string Line = sr.ReadLine();
                                String[] LineValues;
                                LineValues = Line.Split(new char[] { ',' });
                                if (LineValues.Count() == 1)
                                {
                                    String temp = LineValues[0];
                                    LineValues = new string[3];
                                    LineValues[0] = temp;
                                    LineValues[1] = sr.ReadLine();
                                    LineValues[2] = sr.ReadLine();
                                    LineValues[1] = LineValues[1].Substring(1);
                                    LineValues[2] = LineValues[2].Substring(1);
                                }
                                if (LineValues[0] == SearchFor[0] && LineValues[1] == SearchFor[1])
                                {
                                    results.Add(LineValues[2]);
                                }
                            }
                            sr.Close();
                        }

                    }
                    if (ColumnValues.Count > 1)
                    {
                        for (int j = 0; j < ColumnValues.Count - 1; j++)
                        {
                            int current = results.Count - 1;
                            String OrVector = OrGate(results[current], results[current - 1]);
                            results.RemoveAt(current);
                            results.RemoveAt(current - 1);
                            results.Add(OrVector);
                        }
                    }
                }
            }
            return results;
        }
        
        private String OrGate(String Vector1, String Vector2)
        {
            String ResultVector = "";
            for (int i = 0; i < Vector1.Length; i++)
            {
                char test = Vector1[i];
                if (Vector1[i].Equals('1') || Vector2[i].Equals('1'))
                    ResultVector = ResultVector + "1";
                else
                    ResultVector = ResultVector + "0";
            }
            return ResultVector;
        }

        private String AndGate(String Vector1, String Vector2)
        {
            String ResultVector = "";
            for (int i = 0; i < Vector1.Length; i++)
            {
                char test = Vector1[i];
                if (Vector1[i].Equals('1') && Vector2[i].Equals('1'))
                    ResultVector = ResultVector + "1";
                else
                    ResultVector = ResultVector + "0";
            }
            return ResultVector;
        }

        public string CreateOutputVector(XmlDocument xmlDoc, List<string> vectors)
        {
            if (xmlDoc == null || vectors == null)
                return null;
            XmlNodeList LogicalOperation = xmlDoc.SelectNodes("DB_EX2_QUERY/Logical_Operation");
            if (LogicalOperation[0] == null)
                return vectors[0];
            string result = "";
            int Operation = -1;
            if (LogicalOperation.Count == 1)
            {
                String OperationString = LogicalOperation[0].InnerText;
                if (OperationString == "AND")
                    Operation = 1;
                if (OperationString == "OR")
                    Operation = 0;
                while (vectors.Count > 1)
                {
                    for (int i = 0; i < vectors.Count - 1; i++)
                    {
                        String NewVector = "";
                        int current = vectors.Count - 1;
                        switch (Operation)
                        {
                            case 0: { NewVector = OrGate(vectors[current], vectors[current - 1]); break; }
                            case 1: { NewVector = AndGate(vectors[current], vectors[current - 1]); break; }
                            case -1: { return result; }
                        }
                        vectors.RemoveAt(current);
                        vectors.RemoveAt(current - 1);
                        vectors.Add(NewVector);
                    }
                }
                result = vectors[0];
            }
            return result;
        }
        

        public List<string> SelectRecords(string DBFilePath, string outputVector)
        {
            if (DBFilePath == null || outputVector == null)
                return null;
            List<string> records = new List<string>();
            String[][] Lines = File.ReadLines(DBFilePath).Where(line => line != "" && line != null && line != ",,,,,,,,,").Select(x => x.Split(',')).ToArray();
            String[] IDs = new string[Lines.Length];
            for (int i = 1; i < Lines.Length; i++)
            {
                IDs[i] = Lines[i][0];
            }
            for (int i = 0; i < outputVector.Length; i++)
            {
                if (outputVector[i].Equals('1'))
                {
                    records.Add(IDs[i + 1]);
                }
            }
            return records;
        }
        
        }

        







    }









