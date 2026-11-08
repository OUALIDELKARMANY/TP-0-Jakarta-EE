package ma.emsi.oualid.jsf;

import ma.emsi.oualid.service.GenerateurReponse;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Backing bean pour la page JSF index.xhtml.
 */
@Named
@ViewScoped
public class Bb implements Serializable {

    private String roleSysteme;

    private boolean roleSystemeChangeable = true;

    private List<SelectItem> listeRolesSysteme;

    private String question;

    private String reponse;

    private StringBuilder conversation = new StringBuilder();

    @Inject
    private GenerateurReponse generateurReponse;

    @Inject
    private FacesContext facesContext;

    public Bb() {
    }

    public String getRoleSysteme() {
        return this.roleSysteme;
    }

    public void setRoleSysteme(String roleSysteme) {
        this.roleSysteme = roleSysteme;
    }

    public boolean isRoleSystemeChangeable() {
        return this.roleSystemeChangeable;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return this.reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getConversation() {
        return conversation.toString();
    }

    public void setConversation(String conversation) {
        this.conversation = new StringBuilder(conversation);
    }

    /**
     * Envoie la question au serveur.
     */
    public String envoyer() {

        if (this.question == null || this.question.isBlank()) {

            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Texte question vide",
                    "Il manque le texte de la question"
            );

            facesContext.addMessage(null, message);

            return null;
        }

        if (this.conversation.isEmpty()) {
            this.roleSystemeChangeable = false;
        }

        this.reponse = this.generateurReponse.genererReponse(
                this.question,
                this.roleSysteme
        );

        afficherConversation();

        return null;
    }

    /**
     * Pour commencer une nouvelle conversation.
     */
    public String nouveauChat() {
        return "index";
    }

    /**
     * Affiche l'historique de la conversation.
     */
    private void afficherConversation() {

        this.conversation
                .append("== User:\n")
                .append(question)
                .append("\n== Serveur:\n")
                .append(reponse)
                .append("\n");
    }

    /**
     * Liste des rôles système prédéfinis.
     */
    public List<SelectItem> getRolesSysteme() {

        if (this.listeRolesSysteme == null) {

            this.listeRolesSysteme = new ArrayList<>();

            String role = """
                    You are a helpful assistant. You help the user to find the information they need.
                    If the user type a question, you answer it.
                    """;

            this.listeRolesSysteme.add(
                    new SelectItem(role, "Assistant")
            );

            role = """
                    You are an interpreter. You translate from English to French and from French to English.
                    If the user type a French text, you translate it into English.
                    If the user type an English text, you translate it into French.
                    If the text contains only one to three words, give some examples of usage of these words in English.
                    """;

            this.listeRolesSysteme.add(
                    new SelectItem(role, "Traducteur Anglais-Français")
            );

            role = """
                    Your are a travel guide. If the user type the name of a country or of a town,
                    you tell them what are the main places to visit in the country or the town
                    are you tell them the average price of a meal.
                    """;

            this.listeRolesSysteme.add(
                    new SelectItem(role, "Guide touristique")
            );
        }

        return this.listeRolesSysteme;
    }
}
