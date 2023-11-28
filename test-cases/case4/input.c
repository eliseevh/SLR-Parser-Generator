#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
int64_t __generated_private_readint_buf_array[0]
int64_t* __generated_private_readint_buf() { return __generated_private_readint_buf_array; }
gcd(a, b){if(a==0){return b;

}elsereturn gcd(b%a, a);

}

int main() {
printf("%ld %ld %ld\n", gcd(5, 8), gcd(200, 25), gcd(6, 15));

}