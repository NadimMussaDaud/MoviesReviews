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
5. MOVIE() **DONE**
6. SERIES() **DONE**
7. SHOWS() **DONE**
8. ARTIST() **DONE**
9. CREDITS() **DONE**
10. REVIEW() **DONE**
11. REVIEWS() **DONE**
12. GENRE() **DONE**
13. RELEASED()
14. AVOIDERS()


Nota1: *Diagrama de Classes*

                  PersonInterface

        CineReviewsPackage.Persons.PersonAbstract(implements PersonInterface)
        
    CineReviewsPackage.Persons.AdminClass  CineReviewsPackage.Persons.CriticClass  CineReviewsPackage.Persons.AudienceClass  CineReviewsPackage.Shows.Artist (Todas extends CineReviewsPackage.Persons.PersonAbstract) 
 
Nota2: CineReviewsPackage.Shows contém HashMap<CineReviewsPackage.Shows.Artist artist, String role> 


Nota3: Diretor e Criador são considerados a mesma coisa (UTILIZAR director PARA REFERIR UM DELES)
