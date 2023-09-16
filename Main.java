class Main {
  //registers
  static boolean[] reg0 = {true,true,false,true};
  static boolean[] reg1 = {true,false,false,false};
  static boolean[] reg2 = {false,false,false,false};
  static boolean[] reg3 = {false,false,false,false};
  //ALU
  static boolean[] XOR = new boolean[4];
  static boolean[] ADD = new boolean[4];
  static boolean[] ALUOutput = new boolean[4];
  static boolean isEnabledXOR = false;
  static boolean isEnabledADD = false;
  static boolean negativeFlag = false;
  static boolean overflowFlag = false;
  static boolean[] ALUInputA = new boolean[4];
  static boolean[] ALUInputB = new boolean[4];
  //Program counter/pointer
  static boolean[] counterPointer = {false,false,false,false};
  //Program memory
  /*
0 - Enable ALUInputA
1 - Read ALUInputA
2 - Read ALUInputA
3 - Enable ALUInputB
4 - Read ALUInputB
5 - Read ALUInputB
6 - Enable Write
7 - Write
8 - Write
9 - Enable ADD
10 - Enable XOR
  */
  static final int PROGRAMCOUNT = 11;
  static boolean[][] programLine = new boolean[2][PROGRAMCOUNT];

  
  
  public static void main(String[] args) {
    reg0[0] = true;
    reg0[1] = false;
    reg0[2] = true;
    reg0[3] = false;

    reg1[0] = true;
    reg1[1] = false;
    reg1[2] = false;
    reg1[3] = true;

    programLine[1][0] = true;
    programLine[1][3] = true;
    programLine[1][4] = true;
    programLine[1][6] = true;
    programLine[1][8] = true;
    programLine[1][9] = true;
    
    for(boolean temp:reg2) System.out.println(temp);
    readProgramCounter();
    for(boolean temp:reg2) System.out.println(temp);
    //for(int i = 0; i < 16; i++){
      //programCounterIncrement();
      //System.out.println();
      //for(boolean temp:counterPointer) System.out.println(temp);
    //}
  }//end of main method
  public static void ALU(){
    boolean[] carry = new boolean[5];

    for(int i = 0; i < 4; i++){
      XOR[i] = (ALUInputA[i] != ALUInputB[i]);
      carry[i+1] = (ALUInputA[i] && ALUInputB[i]);

      ADD[i] = (XOR[i] != carry[i]);
      carry[i+1] = (XOR[i] && carry[i]);
    }
    System.out.println("XOR: " + XOR[0] + " " + XOR[1] + " " + XOR[2] + " " + XOR[3]);
    System.out.println("ALUOutput: " + ADD[0] + " " + ADD[1] + " " + ADD[2] + " " + ADD[3]);
    if(isEnabledADD) ALUOutput = ADD;
    if(negativeFlag) System.out.println("Negative");
    if(overflowFlag) System.out.println("Overflow");
  }//end of ALU method
  public static void programCounterIncrement(){
    boolean[] pointerCarry = {true,false,false,false,false};
    boolean[] counterOutput = new boolean[4];
    for(int i = 0; i < 4; i++){
      counterOutput[i] = (counterPointer[i] != pointerCarry[i]);
      pointerCarry[i+1] = (counterPointer[i] && pointerCarry[i]);
    }
    counterPointer = counterOutput;
    readProgramCounter();
  }//end of programCounterIncrement method
  public static void readProgramCounter(){
    if(!counterPointer[0] && !counterPointer[1] && !counterPointer[2] && !counterPointer[3]) runProgramLine(1);
      System.out.println("1");
    if(counterPointer[0] && !counterPointer[1] && !counterPointer[2] && !counterPointer[3])
      System.out.println("2");
    if(!counterPointer[0] && counterPointer[1] && !counterPointer[2] && !counterPointer[3])
      System.out.println("3");
    if(counterPointer[0] && counterPointer[1] && !counterPointer[2] && !counterPointer[3])
      System.out.println("4");
    if(!counterPointer[0] && !counterPointer[1] && counterPointer[2] && !counterPointer[3])
      System.out.println("5");/*
    if(counterPointer[0] && !counterPointer[1] && counterPointer[2] && !counterPointer[3]);
    if(!counterPointer[0] && counterPointer[1] && counterPointer[2] && !counterPointer[3]);
    if(counterPointer[0] && counterPointer[1] && counterPointer[2] && !counterPointer[3]);
    if(!counterPointer[0] && !counterPointer[1] && !counterPointer[2] && counterPointer[3])
    if(counterPointer[0] && !counterPointer[1] && !counterPointer[2] && counterPointer[3]);
    if(!counterPointer[0] && counterPointer[1] && !counterPointer[2] && counterPointer[3]);
    if(counterPointer[0] && counterPointer[1] && !counterPointer[2] && counterPointer[3]);
    if(!counterPointer[0] && !counterPointer[1] && counterPointer[2] && counterPointer[3]);
    if(counterPointer[0] && !counterPointer[1] && counterPointer[2] && counterPointer[3]);
    if(!counterPointer[0] && counterPointer[1] && counterPointer[2] && counterPointer[3]);
    if(counterPointer[0] && counterPointer[1] && counterPointer[2] && counterPointer[3]);
    */
  }
  public static void setCounterPointer(boolean[] set){
    counterPointer = set;
  }
  public static void runProgramLine(int lineNo){
    if(programLine[lineNo][0]){
      if(!programLine[lineNo][1] && !programLine[lineNo][2]) ALUInputA = reg0;
      if(programLine[lineNo][1] && !programLine[lineNo][2]) ALUInputA = reg1;
      if(!programLine[lineNo][1] && programLine[lineNo][2]) ALUInputA = reg2;
      if(programLine[lineNo][1] && programLine[lineNo][2]) ALUInputA = reg3;
    }
    if(programLine[lineNo][3]){
      if(!programLine[lineNo][4] && !programLine[lineNo][5]) ALUInputB = reg0;
      if(programLine[lineNo][4] && !programLine[lineNo][5]) ALUInputB = reg1;
      if(!programLine[lineNo][4] && programLine[lineNo][5]) ALUInputB = reg2;
      if(programLine[lineNo][4] && programLine[lineNo][5]) ALUInputB = reg3;
    }
    isEnabledADD = programLine[lineNo][9];
    isEnabledXOR = programLine[lineNo][10];
    if(programLine[lineNo][6]){
      if(!programLine[lineNo][7] && !programLine[lineNo][8]) reg0 = ALUOutput;
      if(programLine[lineNo][7] && !programLine[lineNo][8]) reg1 = ALUOutput;
      if(!programLine[lineNo][7] && programLine[lineNo][8]) reg2 = ALUOutput;
      if(programLine[lineNo][7] && programLine[lineNo][8]) reg3 = ALUOutput;
    }
  }
}
