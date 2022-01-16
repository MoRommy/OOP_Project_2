#PROIECT_2 POO
###RADULESCU STEFAN - 323CD

<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Din cauza ca output-ul trebuia sa fie ca la proiectul_1, dar aveam de implementat functionalitati
noi, am ales sa imi creez clase separate **ChildDisplay** si **GiftDisplay** pentru afisarea datelor.
Astfel, Acestea au avut campurile de la clasele originale, mai putin cele care nu trebuiau afisate.
De asemenea, am facut un copy constructor la fiecare, pentru deep_copy la date. De mentionat ar mai
fi faptul ca o varianta mai inteligenta ar fi fost sa extind clasele originale cu campurile de care
aveam nevoie, dar deja implementasem mai mult de jumatate din proiect cand mi-am dat seama. <br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Pentru partea de **strategy** am ales sa ordonez lista de copii inainte de asignarea cadourilor.
Asadar, mi-am creeat functia **getChildListByStrategy**, care la randul ei foloseste functii de
ordonare in functie de strategie. La afisare, mai intai sortam copiii crescator dupa id. <br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Pentru partea de elfi am creeat clasa **Elf**, care se ocupa de asignarea cadourilor unui copil.
Functia care se ocupa de acest lucru este **assignGifts**. Aceasta seteaza noul buget al 
copilului in functie de culoarea elfului sau, asigneaza cadoul de la mos Craciun, iar apoi,
in cazul in care acesta are un elf galben, i se asigneaza cadoul primit de la acesta.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Tin sa precizez ca am folosit si git, doar ca nu este public. Pentru detalii suplimentare astept 
mesaj pe M. Teams.