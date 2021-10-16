# Proiect Energy System

## Despre

Proiectare Orientata pe Obiecte, Seriile CA, CD
2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>

Student: Mihai-Eugen Barbu, 325CA [2020-2021]

## Rulare teste

Clasa Test#main
  * ruleaza solutia pe testele din checker/, comparand rezultatele cu cele de referinta
  * ruleaza checkstyle

Detalii despre teste: checker/README

Biblioteci necesare pentru implementare:
* Jackson Core 
* Jackson Databind 
* Jackson Annotations

Tutorial Jackson JSON: 
<https://ocw.cs.pub.ro/courses/poo-ca-cd/laboratoare/tutorial-json-jackson>

## Implementare

### Entitati
***

Clasele sunt structurate folosind urmatoarele pachete:

    > commom
       - Constants -> constante utile in executia programului

    > input
       - InitialData, InConsumer, InDistributor, InProducer 
            -> se ocupa de preluarea datelor initiale

       - Update, ProducerChange, DistributorChange
            -> se ocupa de preluarea noilor date primite lunar

       - InputParser -> preluarea tuturor datelor din simulare

    > output
       - Contract -> generarea unui contract specific unui consumator

       - MonthlyStatus -> asociere luna - lista de id-uri
                ++: utila pentru retinerea lunara a distribuitorilor
                    abonati la un producator

       - OutConsumer, OutDistributor, OutProducer
            -> se ocupa de preluare datelor necesare pentru terminarea simularii

       - OutputParser -> procesarea output-ului corespunzator

    > entity
       - Entity
            -> descrie caracteristicile comune tuturor entitatilor

       - EntityFactory
            -> generarea unei entitati de un anumit tip

       - Consumer
            -> prelucrarea datelor unui consumator

       - Distributor
            -> prelucrarea datelor unui distribuitor

       - Producer
            -> prelucrarea datelor unui producator

       - _interface_ ProducerObservable
            -> utila pentru implementarea Observer Pattern

    > entities
       - Entities
            -> controleaza flow-ul operatiilor tuturor entitatilor

       - Consumers
            -> gestioneaza operatiile consumatorilor

       - Distributors
            -> gestioneaza operatiile distribuitorilor

       - Producers
            -> gestioneaza operatiile producatorilor

       - _interface_ DistributorsObservers
            -> utila pentru implementarea <Observer Pattern>

    > strategies
       - 	_interface_ EnergyStrategy -> interfata comuna pentru strategiile folosite

       - GreenEnergyStrategy, PriceEnergyStrategy, QuantityEnergyStrategy
            -> implementarea strategiilor de alegere a producatorilor de catre distribuitori


### Flow
***

Rularea simularii este implementata in clasa Main#main folosind entitatile descrise anterior:

> Runda initiala

  - se citesc datele de intrare si se determina tipul fiecarei entitati folosind EntityFactory;

  - se creeaza instante de tip Consumer, Distributor, Producer care sunt adaugate
    si in logica claselor Consumers, Distributors, Producers;

  - se initializeaza, pentru fiecare producator, lista de distribuitori aferenta lunii curente (0)

  - se actualizeaza listele corespunzatoare fiecarei strategii - {_green/price/quantity_}Producers  

           -- se sorteaza in functie de criteriile de alegere pentru un producator

  - se realizeaza alegerea producatorilor - chooseProducers()  

        ^ @Distributors
            - se aplica strategia specifica fiecarui distribuitor
            - se actualizeaza, pentru fiecare distribuitor, lista de producatori curenta `energyProducers(id)`
            - se actualizeaza costul de productie - updateProductionCost()

        ^ @Producers
            - se actualizeaza listele - distributorIds - lunii curente ale producatorilor
              prin adaugarea noilor distribuitori
                 -> updateProducers()

  - asemanator etapei 1, se simuleaza tranzactiile dintre distribuitori si consumatori - simulateMonth()  

        ^ @Entities
            - se actualizeaza preturile contractelor distribuitorilor - contractPrices
            - consumatorii aleg distribuitori
            - se realizeaza platile -> payMonthlyInstallment() (@Consumers)
                                    -> processPayments() (@Distributors)

> Lunar

  - se verifica statutul bancar al entitatilor  

         -> checkAllBankrupt() - @Distributors
         -> eliminateBankruptConsumers() - @Consumers

  - se genereaza listele de distribuitori ale producatorilor  

         -> se copiaza listele corespunzatoare lunii anterioare

  - se actualizeaza valorile pentru consumatori si distribuitori - getUpdates()  
  - tot aici se actualizeaza valorile producatorilor

         >> pentru fiecare producator actualizat, se instiinteaza distribuitorii abonati in luna curenta
                                                     ^ changedProducer = true

  - se simuleaza tranzactiile dintre distribuitori si consumatori - simulateMonth()  

         ^ @Entities
           - se actualizeaza preturile contractelor distribuitorilor >contractPrices<
           - consumatorii aleg distribuitori
           - se realizeaza platile -> payMonthlyInstallment() (@Consumers)
                                   -> processPayments() (@Distributors)

  - se actualizeaza listele de strategii - updateStrategyLists()  

  - se realizeaza alegerea producatorilor - chooseProducers()  

         -> pentru fiecare distribuitor care trebuie sa aleaga (changedProducer == true)
             - se elimina din listele producatorilor curenti
             - se aplica strategia corespunzatoare pentru generarea noii liste de producatori
             - se actualizeaza costul de productie

             - pentru fiecare producator din lista obtinuta, se adauga distribuitorul curent in lista sa

> Output 

   - se construiesc listele corespunzatoare fiecarei entitati care sunt preluate de OutputParser

### Elemente de design OOP
***

Am folosit:

> **incapsulare** pentru preluarea si modificarea/actualizarea unor date ale entitatilor, cum ar fi:  

        -- @Consumer -> id-ul distribuitorului curent `currDistributorId`
        -- @Distributor -> actualizarea flag-ului `changedProducer`
        -- @Producer -> modificarile din lista de distribuitori

> **abstractizare** pentru crearea clasei Entity  

        - defineste caracteristicile comune ale tuturor entitatilor

> **mostenire** pentru crearea claselor Consumer, Distributor, Producer  

        ^ extind proprietatile definite in Entity prin adaugarea a noi utilitati

> **polimorfism** in cadrul strategiilor pentru implementarea specifica fiecareia

### Design patterns
***

> _Singleton_ - instantele de InputParser, OutputParser, Entities  

        --> definirea unei singure instante care gestioneaza bazele de date aferente

> _Factory_ - EntityFactory, EnergyStrategyFactory  

        --> crearea obiectelor de un anumit tip

> _Observer_  

        --> am creat interfetele ProducerObservable, DistributorsObservable pentru:
               -- @Producer - fiecare producator va notifica distribuitorii atunci cand este modificat
               -- @Distributors - se actualizeaza distribuitorii care contin un producator modificat

> _Strategy_ - EnergyStrategy  

        --> implementeaza metoda getEnergyProducers() care returneaza o lista de producatori
            in functie de cantitatea de energie necesara >energyNeeded<

        --> in functie de tipul de strategie folosit se parcurg listele corespunzatoare strategiilor
            pana se atinge cantitatea de energie necesara

### Dificultati intalnite, limitari, probleme
***

Am intampinat anumite dificultati in cadrul **actualizarii listelor** de distribuitori ale fiecarui producator,
astfel ca am schimbat implementarea pentru a usura logica de eliminare si adaugare de distribuitori
la producatori si reciproc.

