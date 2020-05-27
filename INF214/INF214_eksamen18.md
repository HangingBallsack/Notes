## <font color=red> Oppgave 1 </font>

a) Alle de mulige memory operations er:
- Prosess 1 leser x fra delte minne (R1 i dette tilfellet) og øker den, skriver så til en (ikke-delt) lokal minne, så skriver den det til x (kalles i dette tilfellet W1)
- Det samme for Prosess 2
- Vi har da: R1, W1, R2, W2

b) Alle mulige verdier av x er:
- x = 1
- x = 1
- x = 0
- x = 0
- x = -1
- x = -1

## Oppgave 2
a) Anta at S er en semafor
- Da vil P(S) sjekke om S er minst 1, og så forminske S med 1, om S ikke er minst 1, så vil P(S) blokkere prosessen til S er minst 1
- V(S) vil øke S med en

b) Meningen med en *``Barrier``* er å passe på at alle tråder i en gruppe har nådd et punkt i programmet før en prosess kan gå videre

c) Thread 1 kan printe "0"

d) *```Future```* sine tre forskjellige tilstander:
- pending (future har ingen verdi enda)
- fulfilled (future har en verdi)
- rejected (future er i en *error* state, og vil aldri få en verdi)


e) I synkronisert meldingsutveksling, så vil senderprocessen blokkere ved 'send melding' frem til mottaker prosessen er klar til å motta.

I asynkron-meldingsutveksling så vil sender-prosessen ikke blokkere men heller fortsette, uansett om den mottar at mottakeren er klar eller ikke. Praktiske grenser (som å nå buffer-størrelsen) kan forårsake at senderen til å blokkere selv i asynkron meldingsuveksling

f) *```Software Transaction Memory(STM)```* er en måte å implementere atomiske blokker. Når du starter en atomisk blokk, så starter en tråd en *transaction*. Iløpet av dette, så vil all skriving og lesing av minnet bli loggført. på slutten av *transactionen* så vil lese-loggen bli sammenlignet med tilstanden til minnet, er minnet uforandret fra når transactionen begynte, så blir transactionen *committed* og loggen av skrivingene er skrevet til minnet. Hvis loggene er ```forandret``` så blir de nullstilt og transactionen blir startet på nytt.

## Oppgave 3
a) Implementer en *monitor* som synkroniserer filosofene bruk av gafler i filosof-problemet, *monitoren* skal implementere de følgende to metodene:

void get_forks(int fork1, int fork2);
void release_forks(int fork1, int fork2);

```java
public class monitor() {
    cond fork_released;

    public table(){
        void get_forks(int fork1, int fork2){
            SYNC;
            while(!fork1 || !Afork2){
                wait(fork_released);
            }
            fork1 && fork2 == True;
        }

        void release_forks(int fork1, int fork2){
            SYNC;
            fork1 == false;
            fork2 == false;
            signal_all(fork_released);
        }
    }
}
```
b) *monitor invariant* av min monito er at forks.size() == 10 og antall TRUE-elementer og antall *fork* er lik.

c) ```get_forks````sjekker atomisk begge *forkene* om de er ledig, og reserverer dem, om de ikke er ledig, så åpnes de opp for andre prosesser.

## Oppgave 4
- *Call*:
  - *Coroutine* call er lik som et funksjonskall, et aktiveringskall er pushet til stacken, et *call* ```lader``` en korutine men ikke mer

- *Suspend*:
  - Kjøringen av en korutine er suspended/utsatt. aktivitetdsloggen er lagret på *heapet* sammen med program-telleren

- *Resume*:
  - Kjøringen av en korutine fortsettes. aktivitetsloggen er kopiert fra *heapet* og pushet tilbake til stacken, programtelleren fortsetter fra den lagrete verdiene

- *Destroy*:
  - Aktivitetsloggen som er blitt brukt/lagret er blir de-allokert. Korutinen kan ikke lenger bli suspended

- *Return*:
  - Lignende som return i en metode/funksjon. loggene blir poppet fra stacken


b) Drag-and-drop-machine

```javascript
funcion dragndrop(box){
    while (true){
        evt = yield(box);
        if (evt.type == "mousedown"){
            evt = yield(box);
            if (evt.type == "mousemove"){
                move(box, evt);
            }
            if (evt.type == "mouseup"){
                break;
            }
        }   
    }
}
```

## Oppgave 5
a) *Hoare* triple: {P} S {Q}
- En *Prediksjon* som holder frem til *S* er ferdig og til slutt tilfredstille *Q* 

## Andre notater / Ikke eksamen
En ```invariant``` er en relasjon som alltid er sant

```ThreadSafe``` betyr at en klasses invariant blir ikke brutt av forskjellige tråder samtidig

```Data-race``` betyr usikker kjøring av en metode etc.
