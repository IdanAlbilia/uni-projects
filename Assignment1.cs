using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using System.Xml.XPath;

namespace ConsoleApplication1
{
    class Assignment1
    {

        //queries
        public XmlNodeList Query1 (XmlDocument xmlDoc)// returns all the movies
        {
            throw new NotImplementedException();
        }

        public XmlNodeList Query2(XmlDocument xmlDoc)// returns all the  movies after 2014
        {
            throw new NotImplementedException();
        }

        public XmlNodeList Query3(XmlDocument xmlDoc, String actorFirstName, String actorLastName)// returns all the awards of all TV-shows of an actor
        {
            throw new NotImplementedException();
        }
        public XmlNodeList Query4(XmlDocument xmlDoc)// returns all the TV-shows with more than one seasons
        {
            throw new NotImplementedException();
        }
        public int Query5(XmlDocument xmlDoc, String genre)// retuens the amount of movies in the genre
        {
            throw new NotImplementedException();
        }
        public int Query6(XmlDocument xmlDoc, String yearOfBirth, int amountOfAwards)// returns the amount of different actors that were born after the year and that have more than the award amount in one movie or one TV-show
        {
            throw new NotImplementedException();
        }
        public XmlNodeList Query7(XmlDocument xmlDoc, int amountOfEpisodes)// returns the TV-shows that have more than the amount of epidods in all its seasons
        {
            throw new NotImplementedException();
        }

        //insertions
        public void InsertTVShow(XmlDocument xmlDoc, String name, String genre, String year)
        {
            throw new NotImplementedException();
        }



        public void InsertMovie(XmlDocument xmlDoc, String name, String genre, String year)
        {
            throw new NotImplementedException();
        }

        public void InsertActorToMovie(XmlDocument xmlDoc, String movieName, String actorFirstName, String actorLastName,
            String actorBirthYear)
        {
            throw new NotImplementedException();
        }

        public void InsertActorToTVShow(XmlDocument xmlDoc, String showName, String actorFirstName, String actorLastName,
    String actorBirthYear)
        {
            throw new NotImplementedException();
        }

        public void InsertSeasonToTVShow(XmlDocument xmlDoc, String showName, String numberOfEpisodes)
        {
            throw new NotImplementedException();
        }

        public void InsertAwardToActorInMovie(XmlDocument xmlDoc, String actorFirstName, String actorLastName,String movieName ,String awardCategory, String yearOfWinning)
        {
            throw new NotImplementedException();

        }

        public void InsertAwardToActorInTVShow(XmlDocument xmlDoc, String actorFirstName, String actorLastName, String showName, String awardCategory, String yearOfWinning)
        {
            throw new NotImplementedException();

        }

        private XmlElement CreateNewXmlElement(XmlDocument xmlDoc, string elemName, string elemValue)
        {
            XmlElement newXmlElem = xmlDoc.CreateElement(elemName);
            newXmlElem.InnerText = elemValue;
            return newXmlElem;
        }

        private void exampleOfCreateXML()
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.LoadXml("<DataBaseImplementationCourse/>");//insert your XML file path here
            XmlElement newXmlElem = xmlDoc.CreateElement("Lecturer");
            newXmlElem.InnerText = "Dr. Robert Moskovitch";
            xmlDoc.FirstChild.AppendChild(newXmlElem);
            newXmlElem = xmlDoc.CreateElement("TeachingAssistants");
            xmlDoc.FirstChild.AppendChild(newXmlElem);
            XmlNode tempXmlNode = newXmlElem;
            newXmlElem = CreateNewXmlElement(xmlDoc, "TeachingAssistant", "TeachingAssistant");
            newXmlElem.InnerText = "Guy Shitrit";
            tempXmlNode.AppendChild(newXmlElem);
            newXmlElem = CreateNewXmlElement(xmlDoc, "TeachingAssistant", "Ofir Dvir");
            tempXmlNode.AppendChild(newXmlElem);

            XmlNode xmlNode = xmlDoc.SelectSingleNode("DataBaseImplementationCourse/TeachingAssistants");
            XmlNodeList xmlNodesList = xmlDoc.SelectNodes("DataBaseImplementationCourse/TeachingAssistants/TeachingAssistant");
        }


        }
    }
