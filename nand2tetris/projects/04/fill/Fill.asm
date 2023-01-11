// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
// Pseudo code
// while true
//     key = keyboardInput
//     if key != 0:
//        if SCREEN[0] != 0: // if the screen is not all white
//           continue
//        else
//          screenVal = -1
//     else
//        if SCREEN[0] = 0: // if the screen is all white
//          continue
//        else
//          screenVal = -1
//
//     for i in (0, 8192]:
//        SCREEN[i] = screenVal

// Initialize
@8192
D=A
@screenSize
M=D

(GETTING_INPUT)
@KBD
D=M
@WHITE
D;JEQ

(BLACK)
@SCREEN
D=M
@GETTING_INPUT
D; JNE // goto keyboard input if the SCREEN[0] != 0
@0
D=A-1
@screenVal
M=D // screenVal = -1
@ASSIGNING
0;JMP

(WHITE)
@SCREEN
D=M
@GETTING_INPUT
D; JEQ // goto keyboard input if the SCREEN[0] == 0
@0
D=A
@screenVal
M=D // screenVal = 0 

(ASSIGNING)
@SCREEN
D=A
@addr
M=D // addr=SCREEN

@0
D=A
@i
M=D // i = 0

(WRITE_LOOP)
@screenVal
D=M
@addr
A=M
M=D  // RAM[addr] = screenVal
@addr
M=M+1 // addr += 1

@screenSize
D=M
@i
M=M+1
D=D-M
@WRITE_LOOP
D;JGT

@GETTING_INPUT
0;JMP // Infinite loop
