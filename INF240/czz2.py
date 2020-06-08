def main():
    field = 3  # ex: 2^(3)
    powers = [1,2,3,4,5,6] #x^1, x^2, x^3, x^4 ...


    classes = []
    for x in powers:
        c = []
        c.append(x)
        for y in powers:
            if x != y:
                if y not in c and calc(field, x, y):
                    c.append(y)
        c.sort()
        if c not in classes:            
            classes.append(c)

    for i in range(len(classes)):
        print(classes[i])
        
def GCD(x, y): 
    if x > y: 
        small = y 
    else: 
        small = x 
    for i in range(1, small+1): 
        if((x % i == 0) and (y % i == 0)): 
            gcd = i
    return gcd 

def calc(field, d, e):
    for i in range(field):
        if(d == (2**i)*e % ((2**field)-1)):
            return True


    if(GCD(e, ((2**field)-1)) == 1):
        for i in range(field):
            if(d == ((2**i)/e) % (2**field)-1):
                return True
    
    return False


main()