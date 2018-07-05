import java.util.ArrayList;

public class ParseString {

    private ArrayList<ArrayList<Double>> myAlphaPars = new ArrayList<ArrayList<Double>>();

    /**
     * @return the myAlphaPars
     */
    public  ArrayList<ArrayList<Double>> getMyAlphaPars() {
        return myAlphaPars;
    }

    public static boolean isNumeric(String inputData) {
        return inputData.matches("[-+]?\\d+(\\.\\d+)?");
    }

    public void parsing(String[] myStringArray) {
        
        String myString;

        for (int i = 0; i < myStringArray.length; ++i) {
            ArrayList<Double> row = new ArrayList<>();
            myString = myStringArray[i];

            if (myString.equals("∑")) {//Sum symbole
                row.add(61.0);
                row.add(0.0);
            } else if (myString.equals("∏")) {//Prod symbole
                row.add(62.0);
                row.add(0.0);
            } else if (myString.equals("∘")) {//Comp Funct symbole
                row.add(71.0);
                row.add(0.0);
            } else if (myString.equals("(")) {//LefPara symbole
                row.add(72.0);
                row.add(0.0);
            } else if (myString.equals(")")) {//RightPara symbole
                row.add(73.0);
                row.add(0.0);
            } else if (myString.contains("x") && !(myString.contains("sup") || myString.contains("sub")) && !(myString.contains("ln"))) {// Linear function
                Double slope = Double.parseDouble(myString.substring(0, myString.length()-1));
                row.add(1.0);
                row.add((slope.doubleValue()));
            } else if (myString.contains("log")) {//Logarithm function
                Double base = Double.parseDouble(myString.substring(11, myString.length() - 6));
                row.add(2.0);
                if(base == 2.0){
                row.add(1.0);
                }else if(base == 10.0){
                    row.add(2.0);
                }
            }else if(myString.contains("ln")){
                row.add(2.0);
                row.add(3.0);
            }
            else if (isNumeric(myString)) {// Constant function
                Double myValue = Double.parseDouble(myString);
                row.add(11.0);
                row.add(myValue);
            }else if (myString.equals("+")) {// + operator
                row.add(51.0);
                row.add(0.0);
            }else if (myString.equals("-")) {// - operator
                row.add(52.0);
                row.add(0.0);
            }else if (myString.equals("*")) {// * operator
                row.add(53.0);
                row.add(0.0);
            }else if (myString.equals("/")) {// / operator
                row.add(54.0);
                row.add(0.0);
            }else if (myString.startsWith("x") && myString.endsWith("</sup>")) {// Pow function
                Integer base = Integer.parseInt(myString.substring(6, myString.length()-6));
                row.add(3.0);
                row.add(base.doubleValue());
            }else if (myString.endsWith("<sup>x</sup>")) {// Exp function
                Double base = Double.parseDouble(myString.substring(0,myString.length()-12));
                row.add(4.0);
                row.add(base.doubleValue());
            }
            this.myAlphaPars.add(row);
        }
    }

}

