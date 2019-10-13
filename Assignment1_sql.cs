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
            XmlNodeList ans = xmlDoc.SelectNodes("Netflix/movies/movie");
            return ans;
        }

        public XmlNodeList Query2(XmlDocument xmlDoc)// returns all the  movies after 2014
        {
            XmlNodeList ans = xmlDoc.SelectNodes("Netflix/movies/movie[year>'2014']");
            return ans;
        }

        public XmlNodeList Query3(XmlDocument xmlDoc, String actorFirstName, String actorLastName)// returns all the awards of all TV-shows of an actor
        {
            XmlNodeList ans = xmlDoc.SelectNodes("Netflix/TV-shows/TV-show/actors/actor[first-name='" + actorFirstName + "' and last-name='" + actorLastName + "']/awards/award");
            return ans;
        }
        public XmlNodeList Query4(XmlDocument xmlDoc)// returns all the TV-shows with more than one seasons
        {
            XmlNodeList ans = xmlDoc.SelectNodes("Netflix/TV-shows/TV-show[count(./ seasons / season)>1]");
            return ans;
        }

        public int Query5(XmlDocument xmlDoc, String genre)// retuens the amount of movies in the genre
        {
            XmlNodeList MoviesInGenre = xmlDoc.SelectNodes("Netflix/movies/movie[genre='"+genre+"']");
            int ans = MoviesInGenre.Count;
            return ans;
        }

        public int Query6(XmlDocument xmlDoc, String yearOfBirth, int amountOfAwards)// returns the amount of different actors that were born after the year and that have more than the award amount in one movie or one TV-show
        {
            XmlNodeList ActorsinShows = xmlDoc.SelectNodes("//actors/actor[year-of-birth>='" + yearOfBirth + "' and count(./awards/award)>"+amountOfAwards+ "]");
            int ans = ActorsinShows.Cast<XmlNode>().Select(a => a.InnerText).Distinct().Count();
            return ans;
        }

        public XmlNodeList Query7(XmlDocument xmlDoc, int amountOfEpisodes)// returns the TV-shows that have more than the amount of epidods in all its seasons
        {
            XmlNodeList ans = xmlDoc.SelectNodes("Netflix/TV-shows/TV-show[sum(seasons/season)>'" + amountOfEpisodes + "']");
            return ans;
        }

        //insertions
        public void InsertTVShow(XmlDocument xmlDoc, String name, String genre, String year)
        {
            if (name != null && genre != null && year != null)
            {
                XmlNode Show = xmlDoc.SelectSingleNode("Netflix/TV-shows/TV-show[name='" + name + "']");
                if (Show == null)
                {
                    XmlNode Type = xmlDoc.SelectSingleNode("Netflix/TV-shows");
                    XmlNode NewShow = xmlDoc.CreateElement("TV-show");
                    XmlElement showName = CreateNewXmlElement(xmlDoc, "name", name);
                    NewShow.AppendChild(showName);
                    XmlElement ShowGenre = CreateNewXmlElement(xmlDoc, "genre", genre);
                    NewShow.AppendChild(ShowGenre);
                    XmlElement ShowYear = CreateNewXmlElement(xmlDoc, "year", year);
                    NewShow.AppendChild(ShowYear);
                    Type.AppendChild(NewShow);
                }
            }
        }



        public void InsertMovie(XmlDocument xmlDoc, String name, String genre, String year)
        {
            if (name != null && genre != null && year != null)
            {
                XmlNode Movie = xmlDoc.SelectSingleNode("Netflix/movies/movie[name='" + name + "']");
                if (Movie == null)
                {
                    XmlNode Type = xmlDoc.SelectSingleNode("Netflix/movies");
                    XmlNode NewMovie = xmlDoc.CreateElement("movie");
                    XmlElement MovieName = CreateNewXmlElement(xmlDoc, "name", name);
                    NewMovie.AppendChild(MovieName);
                    XmlElement MovieGenre = CreateNewXmlElement(xmlDoc, "genre", genre);
                    NewMovie.AppendChild(MovieGenre);
                    XmlElement MovieYear = CreateNewXmlElement(xmlDoc, "year", year);
                    NewMovie.AppendChild(MovieYear);
                    Type.AppendChild(NewMovie);
                }
            }
        }

        public void InsertActorToMovie(XmlDocument xmlDoc, String movieName, String actorFirstName, String actorLastName,
            String actorBirthYear)
        {
            if (movieName != null && actorFirstName != null && actorLastName != null && actorBirthYear != null)
            {
                XmlNode Movie = xmlDoc.SelectSingleNode("Netflix/movies/movie[name='" + movieName + "']");
                if (Movie != null)
                {
                    XmlNode Actors = xmlDoc.SelectSingleNode("Netflix/movies/movie[name='" + movieName + "']/actors");
                    if (Actors != null)
                    {
                        XmlNode NewActor = xmlDoc.CreateElement("actor");
                        XmlElement ActorNameF = CreateNewXmlElement(xmlDoc, "first-name", actorFirstName);
                        NewActor.AppendChild(ActorNameF);
                        XmlElement ActorNameL = CreateNewXmlElement(xmlDoc, "last-name", actorLastName);
                        NewActor.AppendChild(ActorNameL);
                        XmlElement ActorYearB = CreateNewXmlElement(xmlDoc, "year-of-birth", actorBirthYear);
                        NewActor.AppendChild(ActorYearB);
                        Actors.AppendChild(NewActor);
                    }
                    else
                    {
                        XmlNode NewActors = xmlDoc.CreateElement("actors");
                        XmlNode NewActor = xmlDoc.CreateElement("actor");
                        XmlElement ActorNameF = CreateNewXmlElement(xmlDoc, "first-name", actorFirstName);
                        NewActor.AppendChild(ActorNameF);
                        XmlElement ActorNameL = CreateNewXmlElement(xmlDoc, "last-name", actorLastName);
                        NewActor.AppendChild(ActorNameL);
                        XmlElement ActorYearB = CreateNewXmlElement(xmlDoc, "year-of-birth", actorBirthYear);
                        NewActor.AppendChild(ActorYearB);
                        NewActors.AppendChild(NewActor);
                        Movie.AppendChild(NewActors);
                    }

                }
            }
        }

        public void InsertActorToTVShow(XmlDocument xmlDoc, String showName, String actorFirstName, String actorLastName,
    String actorBirthYear)
        {
            if (showName != null && actorFirstName != null && actorLastName != null && actorBirthYear != null)
            {
                XmlNode Show = xmlDoc.SelectSingleNode("Netflix/TV-shows/TV-show[name='" + showName + "']");
                if (Show != null)
                {
                    XmlNode Actors = xmlDoc.SelectSingleNode("Netflix/TV-shows/TV-show[name='" + showName + "']/actors");
                    if (Actors != null)
                    {

                        XmlNode NewActor = xmlDoc.CreateElement("actor");
                        XmlElement ActorNameF = CreateNewXmlElement(xmlDoc, "first-name", actorFirstName);
                        NewActor.AppendChild(ActorNameF);
                        XmlElement ActorNameL = CreateNewXmlElement(xmlDoc, "last-name", actorLastName);
                        NewActor.AppendChild(ActorNameL);
                        XmlElement ActorYearB = CreateNewXmlElement(xmlDoc, "year-of-birth", actorBirthYear);
                        NewActor.AppendChild(ActorYearB);
                        Actors.AppendChild(NewActor);
                    }
                    else
                    {
                        XmlNode NewActors = xmlDoc.CreateElement("actors");
                        XmlNode NewActor = xmlDoc.CreateElement("actor");
                        XmlElement ActorNameF = CreateNewXmlElement(xmlDoc, "first-name", actorFirstName);
                        NewActor.AppendChild(ActorNameF);
                        XmlElement ActorNameL = CreateNewXmlElement(xmlDoc, "last-name", actorLastName);
                        NewActor.AppendChild(ActorNameL);
                        XmlElement ActorYearB = CreateNewXmlElement(xmlDoc, "year-of-birth", actorBirthYear);
                        NewActor.AppendChild(ActorYearB);
                        NewActors.AppendChild(NewActor);
                        Show.AppendChild(NewActors);
                    }
                }
            }
        }

        public void InsertSeasonToTVShow(XmlDocument xmlDoc, String showName, String numberOfEpisodes)
        {
            if (showName != null && numberOfEpisodes != null)
            {
                XmlNode Show = xmlDoc.SelectSingleNode("Netflix/TV-shows/TV-show[name='" + showName + "']");
                if (Show != null)
                {
                    XmlNode ShowSeasons = xmlDoc.SelectSingleNode("Netflix/TV-shows/TV-show[name='" + showName + "']/seasons");
                    if (ShowSeasons != null)
                    {
                        XmlNode NewSeason = xmlDoc.CreateElement("season");
                        XmlElement NumofEps = CreateNewXmlElement(xmlDoc, "episodes", numberOfEpisodes);
                        NewSeason.AppendChild(NumofEps);
                        ShowSeasons.AppendChild(NewSeason);
                    }
                   else
                    {
                        XmlNode NewSeasons = xmlDoc.CreateElement("seasons");
                        XmlNode NewSeason = xmlDoc.CreateElement("season");
                        XmlElement NumofEps = CreateNewXmlElement(xmlDoc, "episodes", numberOfEpisodes);
                        NewSeason.AppendChild(NumofEps);
                        NewSeasons.AppendChild(NewSeason);
                        Show.AppendChild(NewSeasons);
                    }
                }
            }
        }

        public void InsertAwardToActorInMovie(XmlDocument xmlDoc, String actorFirstName, String actorLastName,String movieName ,String awardCategory, String yearOfWinning)
        {
            if (actorFirstName != null && actorLastName != null && movieName != null && awardCategory != null && yearOfWinning != null)
            {
                XmlNode ActorInMovie = xmlDoc.SelectSingleNode("Netflix/movies/movie[name='" + movieName + "']/actors/actor[first-name='" + actorFirstName + "' and last-name='" + actorLastName + "']");
                if (ActorInMovie !=null)
                {
                    XmlNode AwardsActorInMovie = xmlDoc.SelectSingleNode("Netflix/movies/movie[name='" + movieName + "']/actors/actor[first-name='" + actorFirstName + "' and last-name='" + actorLastName + "']/awards");
                    if (AwardsActorInMovie != null)
                    {
                        XmlNode NewAward = xmlDoc.CreateElement("award");
                        XmlElement AwardCat = CreateNewXmlElement(xmlDoc, "category", awardCategory);
                        NewAward.AppendChild(AwardCat);
                        XmlElement AwardYear = CreateNewXmlElement(xmlDoc, "year", yearOfWinning);
                        NewAward.AppendChild(AwardYear);
                        AwardsActorInMovie.AppendChild(NewAward);
                    }
                    else
                    {
                        XmlNode NewAwards = xmlDoc.CreateElement("awards");
                        XmlNode NewAward = xmlDoc.CreateElement("award");
                        XmlElement AwardCat = CreateNewXmlElement(xmlDoc, "category", awardCategory);
                        NewAward.AppendChild(AwardCat);
                        XmlElement AwardYear = CreateNewXmlElement(xmlDoc, "year", yearOfWinning);
                        NewAward.AppendChild(AwardYear);
                        NewAwards.AppendChild(NewAward);
                        ActorInMovie.AppendChild(NewAwards);
                    }
                }
                   
            }
        }

        public void InsertAwardToActorInTVShow(XmlDocument xmlDoc, String actorFirstName, String actorLastName, String showName, String awardCategory, String yearOfWinning)
        {
            if (actorFirstName != null && actorLastName != null && showName != null && awardCategory != null && yearOfWinning != null)
            {
                XmlNode ActorInShow = xmlDoc.SelectSingleNode("Netflix/TV-shows/TV-show[name='" + showName + "']/actors/actor[first-name='" + actorFirstName + "' and last-name='" + actorLastName + "']");
                if (ActorInShow != null)
                {
                    XmlNode AwardsActorInShow = xmlDoc.SelectSingleNode("Netflix/TV-shows/TV-show[name='" + showName + "']/actors/actor[first-name='" + actorFirstName + "' and last-name='" + actorLastName + "']/awards");
                    if (AwardsActorInShow != null)
                    {
                        XmlNode NewAward = xmlDoc.CreateElement("award");
                        XmlElement AwardCat = CreateNewXmlElement(xmlDoc, "category", awardCategory);
                        NewAward.AppendChild(AwardCat);
                        XmlElement AwardYear = CreateNewXmlElement(xmlDoc, "year", yearOfWinning);
                        NewAward.AppendChild(AwardYear);
                        AwardsActorInShow.AppendChild(NewAward);
                    }
                    else
                    {
                        XmlNode NewAwards = xmlDoc.CreateElement("awards");
                        XmlNode NewAward = xmlDoc.CreateElement("award");
                        XmlElement AwardCat = CreateNewXmlElement(xmlDoc, "category", awardCategory);
                        NewAward.AppendChild(AwardCat);
                        XmlElement AwardYear = CreateNewXmlElement(xmlDoc, "year", yearOfWinning);
                        NewAward.AppendChild(AwardYear);
                        NewAwards.AppendChild(NewAward);
                        ActorInShow.AppendChild(NewAwards);
                    }
                }

            }
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
