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
5. MOVIE() Nadim
6. SERIES()
7. SHOWS() Nadim
8. ARTIST() Nadim
9. CREDITS()
10. REVIEW()
11. REVIEWS()
12. GENRE()
13. RELEASED()
14. AVOIDERS()


Nota1: *Diagrama de Classes*

                  PersonInterface

        CineReviewsPackage.Persons.PersonAbstract(implements PersonInterface)
        
    CineReviewsPackage.Persons.AdminClass  CineReviewsPackage.Persons.CriticClass  CineReviewsPackage.Persons.AudienceClass  CineReviewsPackage.Persons.ArtistClass (Todas extends CineReviewsPackage.Persons.PersonAbstract) 
 
Nota2: CineReviewsPackage.Shows contém HashMap<CineReviewsPackage.Persons.ArtistClass artist, String role> 


Nota3: Diretor e Criador são considerados a mesma coisa (UTILIZAR director PARA REFERIR UM DELES)
