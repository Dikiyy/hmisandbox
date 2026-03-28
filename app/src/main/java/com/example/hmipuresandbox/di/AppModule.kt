package com.example.hmipuresandbox.di

import com.example.hmipuresandbox.core.common.AppDispatchers
import com.example.hmipuresandbox.data.vehicle.VehicleRepositoryImpl
import com.example.hmipuresandbox.domain.repository.IVehicleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt DI module — wires interfaces to their implementations.
 *
 * --- Spring analogy ---
 * This is the Android equivalent of a Spring @Configuration class.
 *
 * Spring:
 *   @Configuration
 *   class AppConfig {
 *       @Bean fun vehicleRepository(): IVehicleRepository = VehicleRepositoryImpl()
 *       @Bean fun taskExecutor(): AppDispatchers = AppDispatchers()
 *   }
 *
 * Hilt:
 *   @Module
 *   @InstallIn(SingletonComponent::class)  // <- equivalent to ApplicationContext scope
 *   abstract class AppModule {
 *       @Binds fun bindVehicleRepository(impl: VehicleRepositoryImpl): IVehicleRepository
 *   }
 *
 * --- @Binds vs @Provides ---
 * @Binds: tells Hilt "when someone asks for IVehicleRepository, give them VehicleRepositoryImpl".
 *         No body needed — Hilt generates the wiring. Compile-time safe. Preferred.
 *         Spring equivalent: @Bean returning an implementation of an interface.
 *
 * @Provides: used for objects you construct manually (third-party classes, builders, etc.)
 *            Spring equivalent: @Bean on a method with manual construction.
 *
 * --- @InstallIn(SingletonComponent::class) ---
 * Means these bindings live for the whole application lifetime.
 * Spring equivalent: default singleton scope (@Scope("singleton")).
 * Hilt has other scopes: ActivityComponent (request scope), ViewModelComponent, etc.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    /**
     * Binds the data layer implementation to the domain layer interface.
     * No code body — Hilt generates the delegation at compile time.
     */
    @Binds
    @Singleton
    abstract fun bindVehicleRepository(impl: VehicleRepositoryImpl): IVehicleRepository

    companion object {

        /**
         * Provides a single AppDispatchers instance for the whole app.
         * Inject this wherever you need to control which thread a coroutine runs on.
         * In tests, replace this binding with TestDispatchers.
         */
        @Provides
        @Singleton
        fun provideAppDispatchers(): AppDispatchers = AppDispatchers()
    }
}
