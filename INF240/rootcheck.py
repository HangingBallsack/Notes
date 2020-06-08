Field = 3 # ex: 2^(3)

roots = []
for i in range(Field):
    result= ((i**3) + (4*i**2) + (3*i) + 2) % Field # i3 * 4i^2 + 3i +2
    if result == 0:
        roots.append(i)

    print("For input", i, " f(x)= ", result)

if not roots:
    print("No roots found")
else:
    print("Roots found for x =", roots)