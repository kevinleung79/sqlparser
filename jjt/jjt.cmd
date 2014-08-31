call jjtree -OUTPUT_DIRECTORY:../src/selectParser/parser selectParser.jjt
pause
call javacc -OUTPUT_DIRECTORY:../src/selectParser/parser ../src/selectParser/parser/selectParser.jj
pause