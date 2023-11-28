#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
int64_t __generated_private_readint_buf_array[0]
int64_t* __generated_private_readint_buf() { return __generated_private_readint_buf_array; }
max(a, b){if(a<b){return b;

}
return a;

}
min(a, b){return a+b-max(a, b);

}

int main() {
int64_t i=0;
for (int64_t i = min(1000, 10);i < max(1000, 10); i++) {int64_t j=i+10;
printf("%ld\n", j);

}

}