package player.dao;

import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import player.Player;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code PlayerDaoImpl} is able to deserialize {@code Player}
 * instances from an XML source and serialize {@code player} instances to an XML.
 * This is done by using DOM.
 *
 */
public class PlayerDAOImpl implements PlayerDAO {
    private static Logger logger = LoggerFactory.getLogger(PlayerDAOImpl.class);

    private Path dirPath = Paths.get(System.getProperty("user.home"), ".nacjac");
    private String fileName = "database.xml";
    private Path filePath = Paths.get(System.getProperty("user.home"),  ".nacjac", fileName);
    private File file = filePath.toFile();


    /**
     * Save the specified {@code Player} to the database.
     * There are 3 different case.
     * If the specified path of the XML is doesn't exist,
     * we simple save the {@code Player} to a new XML file.
     * If the path exist and the player's name already defined in the XML,
     * we update that specified player otherwise we append it to the XML.
     * @param player who will be saved
     */
    @Override
    public void savePlayer(Player player) {
        if (!file.exists()) {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            try {

                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document database = documentBuilder.newDocument();

                Element rootElement = database.createElement("players");
                database.appendChild(rootElement);
                Element playerElement = database.createElement("player");
                rootElement.appendChild(playerElement);

                Element playerNameElement = database.createElement("name");
                playerNameElement.appendChild(database.createTextNode(player.getName()));
                playerElement.appendChild(playerNameElement);

                Element highScoreElement = database.createElement("highscore");
                highScoreElement.appendChild(database.createTextNode(Integer.toString(player.getHighScore())));
                playerElement.appendChild(highScoreElement);

                Element scoreElement = database.createElement("score");
                scoreElement.appendChild(database.createTextNode(Integer.toString(player.getTotalScore())));
                playerElement.appendChild(scoreElement);

                Element deathElement = database.createElement("death");
                deathElement.appendChild(database.createTextNode(Integer.toString(player.getTotalDeaths())));
                playerElement.appendChild(deathElement);

                Element ghostElement = database.createElement("ghost");
                ghostElement.appendChild(database.createTextNode(Integer.toString(player.getTotalGhostEated())));
                playerElement.appendChild(ghostElement);

                Element ballElement = database.createElement("ball");
                ballElement.appendChild(database.createTextNode(Integer.toString(player.getTotalBallEated())));
                playerElement.appendChild(ballElement);

                Element levelElement = database.createElement("level");
                levelElement.appendChild(database.createTextNode(Integer.toString(player.getTotalLevelCleared())));
                playerElement.appendChild(levelElement);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                DOMSource source = new DOMSource(database);

                if(!dirPath.toFile().exists()){
                    dirPath.toFile().mkdir();
                    logger.info(" .nacjac folder doesn't exist so we created it");
                }


                file.createNewFile();


                StreamResult result = new StreamResult(file);

                transformer.transform(source, result);



            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            try {

                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document database = documentBuilder.parse(file);

                NodeList nodeList = database.getElementsByTagName("player");
                int index = existingUserIndex(nodeList, player);
                if (index != -1) {
                    Element playerElement = (Element) nodeList.item(index);

                    playerElement.getElementsByTagName("highscore").item(0).setTextContent(Integer.toString(player.getHighScore()));
                    playerElement.getElementsByTagName("score").item(0).setTextContent(Integer.toString(player.getTotalScore()));
                    playerElement.getElementsByTagName("ball").item(0).setTextContent(Integer.toString(player.getTotalBallEated()));
                    playerElement.getElementsByTagName("death").item(0).setTextContent(Integer.toString(player.getTotalDeaths()));

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();

                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                    DOMSource source = new DOMSource(database);
                    StreamResult result = new StreamResult(file);

                    transformer.transform(source, result);
                }
                else {
                    NodeList rootList = database.getElementsByTagName("players");
                    Node rootElement = rootList.item(0);
                    Element playerElement = database.createElement("player");
                    rootElement.appendChild(playerElement);

                    Element playerNameElement = database.createElement("name");
                    System.out.println(player.getName());
                    playerNameElement.appendChild(database.createTextNode(player.getName()));
                    playerElement.appendChild(playerNameElement);

                    Element highScoreElement = database.createElement("highscore");
                    highScoreElement.appendChild(database.createTextNode(Integer.toString(player.getHighScore())));
                    playerElement.appendChild(highScoreElement);

                    Element scoreElement = database.createElement("score");
                    scoreElement.appendChild(database.createTextNode(Integer.toString(player.getTotalScore())));
                    playerElement.appendChild(scoreElement);

                    Element deathElement = database.createElement("death");
                    deathElement.appendChild(database.createTextNode(Integer.toString(player.getTotalDeaths())));
                    playerElement.appendChild(deathElement);

                    Element ghostElement = database.createElement("ghost");
                    ghostElement.appendChild(database.createTextNode(Integer.toString(player.getTotalGhostEated())));
                    playerElement.appendChild(ghostElement);

                    Element ballElement = database.createElement("ball");
                    ballElement.appendChild(database.createTextNode(Integer.toString(player.getTotalBallEated())));
                    playerElement.appendChild(ballElement);

                    Element levelElement = database.createElement("level");
                    levelElement.appendChild(database.createTextNode(Integer.toString(player.getTotalLevelCleared())));
                    playerElement.appendChild(levelElement);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();

                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                    DOMSource source = new DOMSource(database);
                    file.createNewFile();

                    StreamResult result = new StreamResult(file);

                    transformer.transform(source, result);

                }

            } catch (ParserConfigurationException e) {
                logger.error("Something went wrong.");
                logger.error(e.getMessage());
                logger.error("Application has been terminated.");
                Platform.exit();
            } catch (SAXException e) {
                logger.error("Something went wrong.");
                logger.error(e.getMessage());
                logger.error("Application has been terminated.");
                Platform.exit();
            } catch (IOException e) {
                logger.error("Something went wrong.");
                logger.error(e.getMessage());
                logger.error("Application has been terminated.");
                Platform.exit();
            } catch (TransformerConfigurationException e) {
                logger.error("Something went wrong.");
                logger.error(e.getMessage());
                logger.error("Application has been terminated.");
                Platform.exit();
            } catch (TransformerException e) {
                logger.error("Something went wrong.");
                logger.error(e.getMessage());
                logger.error("Application has been terminated.");
                Platform.exit();
            }
        }



    }

    /**
     * Gets the list of available {@code Player}s. If the
     * XML file cannot be opened or invalid the method returns {@code null}.
     * @return the list of {@code Player}s or {@code null}
     */
    @Override
    public List<Player> getAllPlayers() {
        List<Player> playerList = new ArrayList<>();
        Document database;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            if (!file.exists()) {
                logger.info("File not found.");
                return null;
            }
            database = documentBuilder.parse(file);
            database.getDocumentElement().normalize();

            NodeList nList = database.getElementsByTagName("player");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    Player player = new Player();

                    player.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                    player.setHighScore(Integer.parseInt(eElement.getElementsByTagName("highscore").item(0).getTextContent()));
                    player.setTotalScore(Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent()));
                    player.setTotalDeaths(Integer.parseInt(eElement.getElementsByTagName("death").item(0).getTextContent()));
                    player.setTotalGhostEated(Integer.parseInt(eElement.getElementsByTagName("ghost").item(0).getTextContent()));
                    player.setTotalLevelCleared(Integer.parseInt(eElement.getElementsByTagName("ball").item(0).getTextContent()));
                    player.setTotalBallEated(Integer.parseInt(eElement.getElementsByTagName("level").item(0).getTextContent()));
                    playerList.add(player);

                }
            }

        } catch (ParserConfigurationException e) {
            logger.error("Something went wrong.");
            logger.error(e.getMessage());
            logger.error("Application has been terminated.");
            Platform.exit();
        } catch (SAXException e) {
            logger.error("Something went wrong.");
            logger.error(e.getMessage());
            logger.error("Application has been terminated.");
            Platform.exit();
        } catch (IOException e) {
            logger.error("Something went wrong.");
            logger.error(e.getMessage());
            logger.error("Application has been terminated.");
            Platform.exit();
        }
        return playerList;
    }

    private int existingUserIndex(NodeList nodeList, Player player) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element playerElement = (Element) nodeList.item(i);
            String name = playerElement.getElementsByTagName("name").item(0).getTextContent();

            if (name.equals(player.getName())) {
                logger.info("User found with the same username.");
               return i;
            }
        }
        logger.info("User not in the database.");
        return -1;
    }
}
