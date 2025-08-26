@echo off
setlocal

rem ==== Ajuste o usuário de domínio aqui, se precisar ====
set DOMAIN_USER=previc\masaru.junior

rem ==== Caminho típico do VS Code (ajuste se estiver em outro lugar) ====
set VSCODE_EXE="C:\Users\DELL\AppData\Local\Programs\Microsoft VS Code\Code.exe"

if not exist %VSCODE_EXE% (
  echo Nao encontrei o VS Code em ^%VSCODE_EXE%^.
  echo Ajuste o caminho do VSCODE_EXE no .bat e tente de novo.
  pause
  exit b 1
)

echo Abrindo VS Code com credenciais de rede de %DOMAIN_USER%...
runas /netonly /user:%DOMAIN_USER% "cmd /c C:\Users\DELL\AppData\Local\Programs\Microsoft VS Code\Code.exe"
