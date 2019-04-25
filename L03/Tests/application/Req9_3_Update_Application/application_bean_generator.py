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
	beans.append('''<bean id="application{}" class="domain.Application">
    <property name="response" value="response02" />
    <property name="link" value="http://link.com" />
    <property name="status" value="SUBMITTED" />
    <property name="position" ref="position02" />
    <property name="problem" ref="problem02" />    
    <property name="hacker" ref= "hacker01"/> 
    <property name="applyMoment" value= "2018/11/10 08:00"/>  
    <property name="creationMoment" value= "2018/12/10 08:00"/>              
    <property name="curricula" ref= "curricula02"/>           
</bean>'''.format(n))

f = open("application_beans.txt", "w+")
separator = "\n"
r = separator.join(beans)
f.write(r)
f.close()