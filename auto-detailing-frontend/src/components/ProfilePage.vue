<template>
  <div class="min-h-screen bg-dark-mode-gray">
    <div class="mx-auto flex max-w-screen-xl flex-col flex-wrap gap-6 p-4">
      <h1 class="border-b-2 pb-2 text-4xl font-medium text-white">USTAWIENIA KONTA</h1>
      <form
        method="post"
        class="update-user-form z-2 flex w-80 flex-col"
        @submit.prevent="updateUser"
      >
        <div class="mx-auto my-2 flex w-full flex-col gap-1 text-white">
          <span class="">Imię</span>
          <InputField
            id="first_name"
            v-model="first_name"
            class="h-10 rounded bg-light-gray pl-2"
            type="text"
            name="first_name"
            required
          />
          <span class="pt-1 text-white">Nazwisko</span>
          <InputField
            id="last_name"
            v-model="last_name"
            class="h-10 rounded bg-light-gray pl-2"
            type="text"
            name="last_name"
            required
          />
          <span class="pt-1 text-white">Numer telefonu</span>
          <InputField
            id="phone_number"
            v-model="phone_number"
            class="h-10 rounded bg-light-gray pl-2"
            type="text"
            name="phone_number"
            required
          />
          <span class="pt-1 text-white">Email</span>
          <InputField
            id="email"
            v-model="email"
            class="h-10 rounded bg-light-gray pl-2"
            type="text"
            name="email"
            required
          />
          <span v-if="isValid" class="pl-2 text-sm text-red-600">{{ error }}</span>
        </div>
        <button
          type="submit"
          class="submit-btn mx-auto w-full rounded-lg bg-gray-500 p-2 text-white hover:bg-mcl-orange"
        >
          Zapisz
        </button>
      </form>
      <div class="mx-auto my-2 flex w-full flex-col text-white">
        <h1 class="mb-6 border-b-2 pb-2 text-4xl font-medium text-white">ZMIEŃ HASŁO</h1>
        <form
          method="post"
          class="update-user-form flex w-80 flex-col gap-1"
          @submit.prevent="updatePassword"
        >
          <span class="text-white">Stare hasło</span>
          <InputField
            id="old_password"
            v-model="old_password"
            class="h-10 rounded bg-light-gray pl-2"
            type="password"
            name="old_password"
            required
          />
          <span class="pt-1 text-white">Nowe hasło</span>
          <InputField
            id="new_password"
            v-model="new_password"
            class="h-10 rounded bg-light-gray pl-2"
            type="password"
            name="new_password"
            required
          />
          <span v-if="passwordTooShort" class="pl-2 text-sm text-red-600">
            Hasło jest za krótkie</span
          >
          <span class="pt-1 text-white">Potwierdź nowe hasło</span>
          <InputField
            id="confirm_new_password"
            v-model="confirm_new_password"
            class="h-10 rounded bg-light-gray pl-2"
            type="password"
            name="confirm_new_password"
            required
          />
          <span v-if="!passwordsEqual" class="pl-2 text-sm text-red-600"> {{ passwordError }}</span>
          <button
            type="submit"
            class="submit-btn mx-auto mt-2 w-full rounded-lg bg-gray-500 p-2 text-white hover:bg-mcl-orange"
          >
            Zmień hasło
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import InputField from '@/components/InputField.vue'
import axios from 'axios'

export default {
  name: 'ProfilePage',
  components: { InputField },
  data() {
    return {
      first_name: '',
      last_name: '',
      phone_number: '',
      email: '',
      old_password: '',
      new_password: '',
      confirm_new_password: '',
      error: '',
      passwordError: '',
      passwordsEqual: true,
      passwordTooShort: false,
      isValid: true
    }
  },
  watch: {
    confirm_new_password: function () {
      this.validatePassword()
    },
    new_password: function () {
      if (this.new_password != '' && this.new_password.length < 4) {
        this.passwordTooShort = true
      } else {
        this.passwordTooShort = false
      }
    }
  },
  async mounted() {
    await this.fetchUser()
  },
  methods: {
    async fetchUser() {
      const response = await axios.get('api/users/user')
      this.first_name = response.data['first_name']
      this.last_name = response.data['last_name']
      this.phone_number = response.data['phone_number']
      this.email = response.data['email']
    },
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

      return true
    },
    validatePassword() {
      if (this.new_password === this.confirm_new_password) {
        // Passwords match, hide the error message
        this.passwordsEqual = true
      } else {
        // Passwords do not match, display the error message
        this.passwordsEqual = false
        this.passwordError = 'Hasła nie są takie same'
      }
    },
    async updateUser() {
      if (!(await this.validateForm())) {
        return false
      } else {
        // const response = await axios.post('api/users/register', {
        //   first_name: this.first_name,
        //   last_name: this.last_name,
        //   phone_number: this.phone_number,
        //   email: this.email,
        //   password: this.password
        // })
        // if (response.status == 200) {
        //   router.push(this.returnUrl || '/login')
        // }
      }
    },
    async updatePassword() {
      // const response = await axios.
    }
  }
}
</script>
