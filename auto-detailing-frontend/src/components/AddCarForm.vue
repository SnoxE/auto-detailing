<template>
  <div class="flex flex-col justify-center text-white">
    <div class="mx-auto mt-10 flex pt-6 text-2xl">
      <span>Dodaj swój samochód</span>
    </div>
    <div class="mx-auto mt-4 flex flex-col">
      <form
        method="post"
        class="login-form z-2 mx-auto flex w-80 flex-col p-6"
        @submit.prevent="addCar"
      >
        <div class="mx-auto my-2 flex w-full flex-col gap-2">
          <InputField
            id="make"
            v-model="make"
            class="mb-2 h-10 rounded bg-light-gray pl-2"
            type="text"
            name="make"
            placeholder="Renault"
            required
          />
          <InputField
            id="model"
            v-model="model"
            class="mb-2 h-10 rounded bg-light-gray pl-2"
            type="text"
            name="model"
            placeholder="Clio 4"
            required
          />
          <InputField
            id="production_year"
            v-model="production_year"
            class="mb-2 h-10 rounded bg-light-gray pl-2"
            type="text"
            name="production_year"
            placeholder="2014"
            required
          />
          <InputField
            id="colour"
            v-model="colour"
            class="mb-1 h-10 rounded bg-light-gray pl-2"
            type="text"
            name="colour"
            placeholder="Czarny"
            required
          />
          <span>Wybierz rozmiar</span>
          <select
            id="selector"
            v-model="size"
            name="selector"
            class="h-10 rounded bg-light-gray pl-2"
          >
            <option v-for="carSize in sizes" :key="carSize">
              {{ carSize }}
            </option>
          </select>
        </div>
        <button
          type="submit"
          class="submit-btn mx-auto mt-6 w-full rounded-lg bg-gray-500 p-2 hover:bg-mcl-orange"
        >
          Dodaj
        </button>
        <span v-if="wasCarAdded" class="mt-1 text-sm text-green-600"
          >Pomyślnie dodano samochód</span
        >
      </form>
    </div>
  </div>
</template>

<script>
import InputField from '@/components/InputField.vue'
import axios from 'axios'

export default {
  name: 'AddCarForm',
  components: { InputField },
  data() {
    return {
      sizes: ['Małe', 'Średnie', 'Duże'],
      userId: '',
      make: '',
      model: '',
      production_year: '',
      colour: '',
      size: '',
      wasCarAdded: false
    }
  },
  methods: {
    async fetchUserId() {
      const response = await axios.get('api/users/user')
      this.userId = response.data['id']
    },
    async addCar() {
      await this.fetchUserId()
      const response = await axios.post('api/users/' + this.userId + '/add-car', {
        make: this.make,
        model: this.model,
        production_year: this.production_year,
        colour: this.colour,
        size: this.size.toUpperCase()
      })

      if (response.status == 200) {
        this.wasCarAdded = true
      }
    }
  }
}
</script>

<script setup>
// import axios from 'axios'
// import { ref } from 'vue'

// const date = ref(new Date())
</script>
