
package ma.emsi.oualid.service;

import jakarta.enterprise.context.Dependent;

import java.io.Serializable;
import java.util.Locale;

@Dependent
public class GenerateurReponse implements Serializable {

    private boolean roleSystemeAffiche = false;

    /**
     * Génère une réponse à une question.
     *
     * @param question La question à modifier.
     * @param roleSysteme Le rôle système à utiliser.
     * @return La question modifiée.
     */
    public String genererReponse(String question, String roleSysteme) {
        String resultat = "||";

        if (!this.roleSystemeAffiche && roleSysteme != null) {
            // Ajouter le rôle système en majuscule au début du résultat.
            resultat += roleSysteme.toUpperCase(Locale.FRENCH) + "\n";
            this.roleSystemeAffiche = true;
        }

        resultat += question.toLowerCase(Locale.FRENCH) + "||";

        return resultat;
    }
}
