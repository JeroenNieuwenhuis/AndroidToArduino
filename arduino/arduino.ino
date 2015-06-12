

String commands, mousc, moush, mousr, mousm, keybp, keybw, keybh, keybr;

void setup() {                
    
  // initialize serial communication at 9600 bits per second:
  Serial1.begin(9600);
  Keyboard.begin();
  Mouse.begin();
  
  mousc = String("mousc");
  moush = String("mousc");
  mousr = String("mousc");
  mousm = String("mousm");
  keybw = String("keybw");
  keybp = String("keybp");
  keybh = String("keybh");
  keybr = String("keybr");
}

void loop() {
  if(Serial1.available() > 0){  
    commands = Serial1.readStringUntil('\n');
    
    String type = commands.substring(0,5);
    if (type.equals(keybw)) {
      for (int i=5; i <= commands.length()-1; i++){
        Keyboard.press(commands.charAt(i));
        Keyboard.releaseAll();
      }
    } else if (type.equals(keybp)) {
      for (int i=5; i <= commands.length()-1; i++){
        Keyboard.press(commands.charAt(i));
      }
      Keyboard.releaseAll();
    } else if (type.equals(keybp)) {
      for (int i=5; i <= commands.length()-1; i++){
        Keyboard.press(commands.charAt(i));
      }
      Keyboard.releaseAll();
    } else if (type.equals(keybp)) {
      for (int i=5; i <= commands.length()-1; i++){
        Keyboard.press(commands.charAt(i));
      }
      Keyboard.releaseAll();
    } else if (type.equals(keybh)) {
      for (int i=5; i <= commands.length()-1; i++){
        Keyboard.press(commands.charAt(i));
      }
    } else if (type.equals(keybr)) {
      Keyboard.releaseAll();
    } else if (type.equals(mousc)) {
      for (int i=5; i <= commands.length()-1; i++){
        if(commands.charAt(i) == 'l'){
          Mouse.click();
          Mouse.release();
        } else if(commands.charAt(i) == 'r'){
          Mouse.click(MOUSE_RIGHT);
          Mouse.release();
        } else if(commands.charAt(i) == 'm'){
          Mouse.click(MOUSE_MIDDLE);
          Mouse.release();
        }
      }
    } else if (type.equals(moush)) {
      for (int i=5; i <= commands.length()-1; i++){
        if(commands.charAt(i) == 'l'){
          Mouse.press();
        } else if(commands.charAt(i) == 'r'){
          Mouse.press(MOUSE_RIGHT);
        } else if(commands.charAt(i) == 'm'){
          Mouse.press(MOUSE_MIDDLE);
        }
      }
    } else if (type.equals(mousr)) {
      Mouse.release();
    } else if (type.equals(mousm)) {
      int mousex = commands.substring(5,11).toInt();
      int mousey = commands.substring(11, 17).toInt();
      int scroll = commands.substring(17, 20).toInt();
      
      Mouse.move(mousex, mousey, scroll);
    }
  }   
    
    //For debugging purpose
    //Serial.println(state);
}
