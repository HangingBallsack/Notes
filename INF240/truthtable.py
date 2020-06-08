x1=1
x2=1
x3=1
x4=1

f_av_x = (1 + x1*x3 + x2*x4 + x1*x2*x3*x4) % 2

print("x1: ", x1, "\nx2: ", x2, "\nx3: ", x3, "\nx4: ", x4, "\nF(x): ", f_av_x)