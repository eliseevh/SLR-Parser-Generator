#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
int64_t __generated_private_readint_buf_array[1]
int64_t* __generated_private_readint_buf() { return __generated_private_readint_buf_array; }
is_prime(a){for (int64_t i = 2;i < 1+a/2; i++) {if(a%i==0){return false;

}

}
return true;

}
print_primes(n){for (int64_t i = 2;i < n; i++) {if(is_prime(i)){printf("%ld\n", i);

}

}

}

int main() {
print_primes(1*(scanf("%ld", __generated_private_readint_buf() + 0), *(__generated_private_readint_buf() + 0)));

}