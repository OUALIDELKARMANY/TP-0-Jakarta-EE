
package ma.emsi.oualid.service;

import jakarta.enterprise.context.Dependent;

import java.io.Serializable;
import java.util.Locale;

@Dependent
public class GenerateurReponse implements Serializable {

    private boolean roleSystemeAffiche = false;

    public String genererReponse(String question, String roleSysteme) {

        StringBuilder resultat = new StringBuilder();

        // Afficher le rôle système une seule fois.
        if (!this.roleSystemeAffiche && roleSysteme != null) {
            resultat.append(roleSysteme.toUpperCase(Locale.FRENCH))
                    .append("\n");
            this.roleSystemeAffiche = true;
        }

        // Nettoyer la question.
        String questionNettoyee = question.trim()
                .replaceAll("\\s+", " ");

        // Préparer la question pour l'analyse.
        String questionMinuscule =
                questionNettoyee.toLowerCase(Locale.FRENCH);

        // Compter les mots.
        String[] mots = questionNettoyee.split("\\s+");
        int nombreMots = mots.length;

        // Compter les caractères.
        int nombreCaracteres = questionNettoyee.length();

        // Identifier le type de question.
        String typeQuestion;

        if (questionMinuscule.startsWith("pourquoi")) {
            typeQuestion = "Question explicative";
        } else if (questionMinuscule.startsWith("comment")) {
            typeQuestion = "Question sur une méthode ou un fonctionnement";
        } else if (questionMinuscule.startsWith("qui")) {
            typeQuestion = "Question sur une personne";
        } else if (questionMinuscule.startsWith("où")) {
            typeQuestion = "Question sur un lieu";
        } else if (questionMinuscule.startsWith("quand")) {
            typeQuestion = "Question temporelle";
        } else if (questionMinuscule.startsWith("combien")) {
            typeQuestion = "Question quantitative";
        } else if (questionMinuscule.startsWith("quel")
                || questionMinuscule.startsWith("quelle")
                || questionMinuscule.startsWith("quels")
                || questionMinuscule.startsWith("quelles")
                || questionMinuscule.startsWith("que")
                || questionMinuscule.startsWith("qu'est-ce")) {
            typeQuestion = "Question informative";
        } else if (questionMinuscule.startsWith("est-ce")) {
            typeQuestion = "Question fermée";
        } else {
            typeQuestion = "Question générale";
        }

        // Déterminer la longueur de la question.
        String longueur;

        if (nombreMots <= 5) {
            longueur = "Question courte";
        } else if (nombreMots <= 12) {
            longueur = "Question moyenne";
        } else {
            longueur = "Question détaillée";
        }

        // Construire la réponse.
        resultat.append("||Analyse de votre question||\n")
                .append("Question : ").append(questionNettoyee).append("\n")
                .append("Nombre de mots : ").append(nombreMots).append("\n")
                .append("Nombre de caractères : ").append(nombreCaracteres).append("\n")
                .append("Type : ").append(typeQuestion).append("\n")
                .append("Longueur : ").append(longueur);

        return resultat.toString();
    }
}
