import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface ElementTextual {
    void setContinut(String continut);
    String afiseaza();
    void cautaSubsirRegexSiAfiseaza(String regex);
}

abstract class ElementTextualAbstract implements ElementTextual {
    abstract String getContinut();

    @Override
    public void cautaSubsirRegexSiAfiseaza(String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(this.getContinut());

        while (matcher.find()) {
            System.out.println("În textul: " + this.getContinut());
            System.out.println("Subșirul găsit: " + matcher.group());
            System.out.println();
        }
    }
}

class Propoz extends ElementTextualAbstract {
    private String continut;

    public Propoz() {
        this.continut = null;
    }

    public Propoz(String continut) {
        this.continut = continut;
    }

    public Propoz(String continut, int n) {
        this.continut = continut.substring(0, Math.min(n, continut.length()));
    }

    @Override
    public String getContinut() {
        return continut;
    }

    @Override
    public String afiseaza() {
        return "(prop=\"" + continut + "\")";
    }

    @Override
    public void setContinut(String continut) {
        this.continut = continut;
    }

    @Override
    public String toString() {
        return "(prop=\"" + continut + "\")";
    }
}

abstract class Paragraf extends ElementTextualAbstract {
    abstract void adaugaPropozitie(Propoz propoz);
}

class ParagrafConcret extends Paragraf {
    private Propoz[] prop;

    public ParagrafConcret() {
        this.prop = null;
    }

    @Override
    void adaugaPropozitie(Propoz propoz) {
        if (prop == null) {
            prop = new Propoz[1];
            prop[0] = propoz;
        } else {
            Propoz[] temp = new Propoz[prop.length + 1];
            System.arraycopy(prop, 0, temp, 0, prop.length);
            temp[prop.length] = propoz;
            prop = temp;
        }
    }

    public Propoz[] getProp() {
        return prop;
    }

    @Override
    String getContinut() {
        StringBuilder sb = new StringBuilder();
        for (Propoz p : prop) {
            sb.append(p.getContinut()).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public String afiseaza() {
        StringBuilder output = new StringBuilder();
        output.append("(");
        for (Propoz t : prop) {
            output.append(t).append(",");
        }
        output.append(getContinut());
        output.append(")");
        return output.toString();
    }

    @Override
    public void setContinut(String continut) {
        if (prop != null && prop.length > 0) {
            prop[0].setContinut(continut);
        }
    }
}

class Pagina {
    private Paragraf[] paragList;

    public Pagina(Paragraf[] paragList) {
        this.paragList = paragList;
    }

    public void afiseazaPagina() {
        StringBuilder output = new StringBuilder("Pagina:\n");
        for (Paragraf parag : paragList) {
            if (parag instanceof ParagrafConcret) {
                Propoz[] propozitii = ((ParagrafConcret) parag).getProp();
                for (Propoz propoz : propozitii) {
                    output.append(propoz.getContinut()).append(" ");
                }
                output.append("\n");
            } else {
                output.append(parag.toString()).append("\n");
            }
        }
        System.out.println(output.toString());
    }

    public void cautaSubsirRegexSiAfiseaza(String regex) {
        for (Paragraf parag : paragList) {
            parag.cautaSubsirRegexSiAfiseaza(regex);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Paragraf[] paragList = null;

        while (true) {
            System.out.println("==================MENU===================");
            System.out.println("1. Creare paragraf și adăugare propoziții");
            System.out.println("2. Afisare pagina");
            System.out.println("3. Cautare subșir cu expresii regulate");
            System.out.println("0. Ieșire");
            System.out.println("=========================================");

            int optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1:
                    System.out.println("Numărul de paragrafe:");
                    int numParag = scanner.nextInt();
                    scanner.nextLine();

                    paragList = new Paragraf[numParag];
                    for (int i = 0; i < numParag; i++) {
                        System.out.println("Paragraf " + (i + 1));
                        paragList[i] = new ParagrafConcret();

                        for (int j = 0; j < 3; j++) {
                            System.out.println("Dati propozitia:");
                            String propozitie = scanner.nextLine();
                            System.out.println("Numărul de caractere:");
                            int n = scanner.nextInt();
                            scanner.nextLine();
                            Propoz propoz = new Propoz(propozitie, n);
                            ((ParagrafConcret) paragList[i]).adaugaPropozitie(propoz);
                        }
                    }
                    break;
                case 2:
                    if (paragList != null) {
                        Pagina pagina = new Pagina(paragList);
                        pagina.afiseazaPagina();
                    } else {
                        System.out.println("Creați mai întâi paragrafele!");
                    }
                    break;
                case 3:
                    if (paragList != null) {
                        System.out.println("Introduceți șablonul de căutare cu expresii regulate:");
                        String regex = scanner.nextLine();
                        Pagina pagina = new Pagina(paragList);
                        pagina.cautaSubsirRegexSiAfiseaza(regex);
                    } else {
                        System.out.println("Creați mai întâi paragrafele!");
                    }
                    break;
                case 0:
                    System.out.println("La revedere!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opțiune invalidă! Selectați o opțiune validă");
                    break;
            }
        }
    }
}
