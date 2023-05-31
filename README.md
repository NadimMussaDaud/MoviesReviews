**ACRESCENTAR O QUE FOR NECESSÁRIO FAZER**

********************
SUGESTÕES SÃO BEM-VINDAS
********************

DICA: INSTALAR **COPILOT**

Qualidade de código:
1. Por exceções nos metodos já feitos E no resto dos métodos.
2. Comentar código ao fazer os métodos.
3. Utilizar Genéricos se for melhor
4. 

Funcionalidade:
1. Exit() **DONE**
2. HELP() **DONE**
3. REGISTER() **DONE**
4. USERS() **DONE**
5. MOVIE() 
6. SERIES()
7. SHOWS()
8. ARTIST()
9. CREDITS()
10. REVIEW()
11. REVIEWS()
12. GENRE()
13. RELEASED()
14. AVOIDERS()


Nota1: *Diagrama de Classes*

                  PersonInterface

        PersonAbstract(implements PersonInterface)
        
    AdminClass  CriticClass  AudienceClass  ArtistClass (Todas extends PersonAbstract) 
 
Nota2: Shows contém HashMap<ArtistClass artist, String role> 
