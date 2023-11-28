#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
int64_t __generated_private_readint_buf_array[3]
int64_t* __generated_private_readint_buf() { return __generated_private_readint_buf_array; }

int main() {
int64_t a=(scanf("%ld", __generated_private_readint_buf() + 0), *(__generated_private_readint_buf() + 0));
int64_t b=(scanf("%ld", __generated_private_readint_buf() + 1), *(__generated_private_readint_buf() + 1));
int64_t tmp=a;
a=b;
b=tmp;
printf("%ld %ld %ld %ld\n", a, b, (scanf("%ld", __generated_private_readint_buf() + 2), *(__generated_private_readint_buf() + 2))&&tmp, tmp);

}