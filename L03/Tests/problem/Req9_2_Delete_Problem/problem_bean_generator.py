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
	beans.append('''<bean id="problem{}" class="domain.Problem">
    <property name="title" value="title03" />
    <property name="statement" value="statement03" />
    <property name="hint" value="hint03" />
    <property name="finalMode" value="false" />
    <property name="attachments" value="attachments03" />  
    <property name="company" ref= "company01"/> 
    <property name="position">
        <list>
            <ref bean="position01"/>
            <ref bean="position02"/>
        </list>
    </property>
</bean>'''.format(n))

f = open("problem_beans.txt", "w+")
separator = "\n"
r = separator.join(beans)
f.write(r)
f.close()