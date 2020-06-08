# Oppg 1
def func1():
    print("\n-------------Oppgave 1-------------")

    while True:
        try:
            x = float(input("Tall nr 1: "))
            y = float(input("Tall nr 2: "))
            z = float(input("Tall nr 3: "))
        except ValueError:
            print("------ Tall =/= bokstav ------ \n")
            continue
        else:
            break

    text = "Disse tallene er "
    if x < y < z:
        print(text + "stigende")
    elif x > y > z:
        print(text + "synkende")
    else:
        print(text + "ingen av delene")



# Oppg 2a
def func2():
    print("\n-------------Oppgave 2-------------")

    D = "Ruter"
    H = "Hjerter"
    S = "Spar"
    C = "Kløver"

    bokstav = str(input("Skriv en av disse: D, H, S eller C\n"))
    if bokstav.upper() == "D":
        print(D)
    elif bokstav.upper() == "H":
        print(H)
    elif bokstav.upper() == "S":
        print(S)
    elif bokstav.upper() == "C":
        print(C)

# Oppg 2b
    kortStokk = {
        "A": "Ess",
        "J": "Knekt",
        "Q": "Dame",
        "K": "Konge",
        "2": "To",
        "3": "Tre",
        "4": "Fire",
        "5": "Fem",
        "6": "Seks",
        "7": "Syv",
        "8": "Åtte",
        "9": "Ni",
        "10": "Ti"
    }
    kort = input("\nTall: 2 - 10 \t eller \t A, J, Q, K\n")
    print(kortStokk[kort.upper()])



# oppg 3
def func3():
    print("\n-------------Oppgave 3-------------")

    ValutaKurser = {
        "EUR": 9.68551,
        "USD": 8.50373,
        "GBP": 11.0134,
        "SEK": 0.92950,
        "AUD": 6.06501,
        "NOK": 1.00000
    }
    kursFra = input("Fra hvilke valuta vil du konvertere? EUR, USD, GBP, SEK, AUD \n")
    kursFraAntall = float(input("Hvor mange " + kursFra.upper() + " vil du konvertere til NOK?\n"))

    konvertert = kursFraAntall * ValutaKurser[kursFra.upper()]
    print(kursFraAntall, kursFra, " er %.2f i NOK." % konvertert)

    konvFraNorsk = float(input("Norske kroner som skal konverteres: "))
    konvFraNorskTil = input("Til hvilke kurs?  EUR, USD, GBP, SEK, AUD\n")
    print(konvFraNorsk, "Norske er %.2f" % (konvFraNorsk/ValutaKurser[konvFraNorskTil.upper()]), konvFraNorskTil.upper())



# oppg 4
def func4():
    print("\n-------------Oppgave 4-------------")

    for i in range (0, 10):
        print(i, " oppøyd i 3:", i**3)



# oppg 5
def func5():
    print("\n-------------Oppgave 5-------------")

    start = int(input("Start: "))
    stopp = int(input("Stopp (til og med): "))
    n = int(input("n: "))

    print("Tall mellom ", start, " og ", stopp, "som er delelig på", n, ":")
    for i in range(stopp+1):
        if (i%n == 0):
            print(i)



# oppg 6
def func6():
    print("\n-------------Oppgave 6-------------")

    print("Celsius \t Fahrenheit \t Status")
    for i in range (11):
        if i < 6:
            print(i*10,"\t\t", tilFahrenheit(i*10), "\t\t Jeg har det bra.")
        else:
            print(i*10,"\t\t", tilFahrenheit(i*10), "\t\t Jeg svetter ihjel!")

# tøysete metode som står i oppgaven skal være med :P :P 
def tilFahrenheit(celcius):
    return celcius*1.8+32


"""
# oppg 7
def func7():

"""


# velg hvilke oppgave som skal vises i terminalen
while True:
    valg = input("\nHvilke oppgave? \t1-7 \tAlle \tAvslutt: 0 \n")
    if valg == "1":
        func1()
    elif valg == "2":
        func2()
    elif valg == "3":
        func3()
    elif valg == "4":
        func4()
    elif valg == "5":
        func5()
    elif valg == "6":
        func6()
    elif valg == "7":
        func7()
    elif valg == "0":
        break
    elif valg.upper() == "ALLE":
        func1()
        func2()
        func3()
        func4()
        func5()
        func6()
        func7()
    else:
        print("Ugylidg, prøv igjen.")
