<template>
  <div class="flex flex-col justify-center text-white">
    <div class="mx-auto mt-10 flex pt-6 text-2xl">
      <span>Nie masz jeszcze konta?</span>
    </div>

    <form
      method="post"
      class="login-form z-2 mx-auto flex w-80 flex-col p-6"
      @submit.prevent="registerUser"
    >
      <div class="mx-auto my-2 flex w-full flex-col gap-4">
        <InputField
          id="first_name"
          v-model="first_name"
          class="h-10 rounded bg-light-gray pl-2"
          type="text"
          name="first_name"
          placeholder="Jan"
          required
        />
        <InputField
          id="last_name"
          v-model="last_name"
          class="h-10 rounded bg-light-gray pl-2"
          type="text"
          name="last_name"
          placeholder="Kowalski"
          required
        />
        <InputField
          id="phone_number"
          v-model="phone_number"
          class="h-10 rounded bg-light-gray pl-2"
          type="text"
          name="phone_number"
          placeholder="123456789"
          required
        />
        <InputField
          id="email"
          v-model="email"
          class="h-10 rounded bg-light-gray pl-2"
          type="text"
          name="email"
          placeholder="you@gmail.com"
          required
        />
        <InputField
          id="password"
          v-model="password"
          class="h-10 rounded bg-light-gray pl-2"
          type="password"
          name="email"
          placeholder="Hasło"
          required
        />
        <span v-if="isValid" class="pl-2 text-sm text-red-600">{{ error }}</span>
      </div>
      <button
        type="submit"
        class="submit-btn mx-auto my-6 w-full rounded-lg bg-gray-500 p-2 hover:bg-mcl-orange"
      >
        Zarejestruj
      </button>
      <div class="mx-auto flex">
        <span class="p-1 text-sm">Masz już konto?</span>
        <router-link
          to="/login"
          class="p-1 text-sm underline underline-offset-2 hover:text-mcl-orange"
          >Zaloguj się</router-link
        >
      </div>
    </form>
  </div>
</template>

<script>
import InputField from '@/components/InputField.vue'
import router from '@/router/index.js'
import axios from 'axios'

export default {
  name: 'RegisterForm',
  components: { InputField },
  data() {
    return {
      first_name: '',
      last_name: '',
      phone_number: '',
      email: '',
      password: '',
      error: '',
      isValid: true
    }
  },
  methods: {
    async emailNotUnique() {
      const response = await axios.get('/api/users/email?email=' + this.email)

      if (response.status == 200) {
        if (response.data > 0) {
          return true
        } else {
          return false
        }
      }
    },
    async validateForm() {
      if (!this.phone_number.match('^[0-9]+$')) {
        this.error = 'Niepoprawny numer telefonu'
        return false
      }

      if (this.phone_number.length < 9) {
        this.error = 'Za krótki numer telefonu'
        return false
      }

      if (this.phone_number.length > 9) {
        this.error = 'Za długi numer telefonu'
        return false
      }

      if (!this.email.match('^(.+)@(.+)$')) {
        this.error = 'Niepoprawny format adresu email'
        return false
      }

      if (await this.emailNotUnique()) {
        this.error = 'Adres email jest już zajęty'
        return false
      }

      if (this.password.length < 3) {
        this.error = 'Hasło jest za krótkie'
        return false
      }

      return true
    },
    async registerUser() {
      if (!(await this.validateForm())) {
        return false
      } else {
        const response = await axios.post('api/users/register', {
          first_name: this.first_name,
          last_name: this.last_name,
          phone_number: this.phone_number,
          email: this.email,
          password: this.password
        })

        if (response.status == 200) {
          router.push(this.returnUrl || '/login')
        }
      }
    }
  }
}
</script>
