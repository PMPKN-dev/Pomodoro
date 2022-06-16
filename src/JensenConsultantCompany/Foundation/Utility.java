package JensenConsultantCompany.Foundation;

public class Utility {

    /**
     * Merges two arrays back-to-back
     * @param first first array to merge
     * @param second second array to merge
     * @return a merged array consisting of First+Second
     */
    public static String[] arrayMerge(String[] first, String[] second){
        int FAL = first.length;
        int SAL = second.length;
        String[] output = new String[FAL+SAL];
        System.arraycopy(first,0,output,0,FAL);
        System.arraycopy(second,0,output,FAL,SAL);
        return output;
    }
}
