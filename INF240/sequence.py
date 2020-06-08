Field = 2
s = [1,0,1,0,0]

n = 0
for i in range(30):
    result = (s[n+3] + s[n+4]) # S_n+x = S_n+3 + S_n+4
    s.append(result % Field)
    n+=1 

print(*s, sep=' ')
