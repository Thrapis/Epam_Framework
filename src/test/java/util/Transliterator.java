package util;

public class Transliterator {

    private final static String[] FromAlphabet = {"а","б","в","г","д","е","ё","ж","з","и",
            "й","к","л","м","н","о","п","р","с","т","у","ф","х","ц","ч","ш","щ","ъ","ы","ь",
            "э","ю","я"," ","/","(",")","+"};

    private final static String[] ToAlphabet = {"a","b","v","g","d","e","yo","zh","z","i",
            "j","k","l","m","n","o","p","r","s","t","u","f","h","c","ch","sh","shch","","y","",
            "e","yu","ya","-","","","",""};

    public static String transliterate(String text) {
        text = text.toLowerCase();

        for (int i = 0; i < FromAlphabet.length; i++) {
            text = text.replace(FromAlphabet[i], ToAlphabet[i]);
        }

        return text;
    }
}
