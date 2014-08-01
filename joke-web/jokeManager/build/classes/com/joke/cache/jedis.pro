#config for jedis use sharde

#base config
#the maximum number of objects that can be borrowed at one time 
maxActive=1000
#the maximum amount of time to wait for an idle object when the pool is exhausted and whenExhaustedAction is WHEN_EXHAUSTED_BLOCK (otherwise ignored)
maxIdle=100
the maximum number of idle objects in my pool 
minIdle=50
#the maximum amount of time to wait for an idle object when the pool is exhausted and whenExhaustedAction is WHEN_EXHAUSTED_BLOCK (otherwise ignored)
#in milliseconds
maxWait=5000

#test
testOnReturn=true

#sharde config
#shared=10.10.26.21:6379:l@uncher!@#$%
shared=127.0.0.1:6379:123
