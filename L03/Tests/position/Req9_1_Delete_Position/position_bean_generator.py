import sys

args = sys.argv

if len(args) != 2:
    raise Exception("Insert bean number as a parameter")
    
NUMBER = 0
try:
    NUMBER = int(sys.argv[1])
except Exception as e: print(e)

beans = []
for n in range(100, NUMBER + 100):
	beans.append('''<bean id="position{}" class="domain.Position">
    <property name="ticker" value="CARM-0167" />
    <property name="title" value="title04" />
    <property name="techs" value="techs04" />
    <property name="status" value="false" />
    <property name="skills" value="skills04" />
    <property name="salary" value= "20.0" />
    <property name="profile" value="profile04" />
    <property name="description" value= "description03"/>     
    <property name="deadline" value= "2020/11/10 08:00"/>      
    <property name="company" ref= "company01"/>  
    <property name="cancel" value="false" />     
</bean>'''.format(n))

f = open("position_beans.txt", "w+")
separator = "\n"
r = separator.join(beans)
f.write(r)
f.close()